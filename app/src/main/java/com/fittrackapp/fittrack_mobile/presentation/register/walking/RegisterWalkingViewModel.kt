package com.fittrackapp.fittrack_mobile.presentation.register

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.seconds

@HiltViewModel
class RegisterWalkingViewModel @Inject constructor(
    private val app: Application
) : ViewModel(), SensorEventListener {

    private val _state = MutableStateFlow(RegisterWalkingViewState())
    val state = _state.asStateFlow()

    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null
    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)

    private var isTimerRunning = false
    private var locationCallback: LocationCallback? = null

    init {
        sensorManager = app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    suspend fun fetchInitialLocation() {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) {
                    _state.value = _state.value.copy(currentLocation = location)
                }
            }
        }
    }

    @androidx.annotation.RequiresPermission(allOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    fun start() {
        if (!_state.value.isLive) {
            // First start: reset state and add first track
            _state.value = RegisterWalkingViewState(
                isLive = true,
                onPause = false,
                targetDuration = _state.value.targetDuration,
                tracks = listOf(emptyList())
            )
        } else if (_state.value.onPause) {
            // Resume: add a new empty track
            _state.value = _state.value.copy(
                isLive = true,
                onPause = false,
                tracks = _state.value.tracks + listOf(emptyList())
            )
        }
        startSensors()
        startLocationUpdates()
        startTimer()
    }

    fun pause() {
        _state.value = _state.value.copy(onPause = true)
        stopSensors()
        stopLocationUpdates()
        isTimerRunning = false
    }

    fun stop() {
        _state.value = RegisterWalkingViewState(targetDuration = _state.value.targetDuration)
        stopSensors()
        stopLocationUpdates()
        isTimerRunning = false
    }

    fun increaseTargetDuration() {
        _state.value = _state.value.copy(targetDuration = _state.value.targetDuration + 60.seconds)
    }

    fun decreaseTargetDuration() {
        val newTarget = (_state.value.targetDuration - 60.seconds).coerceAtLeast(0.seconds)
        _state.value = _state.value.copy(targetDuration = newTarget)
    }

    private fun startSensors() {
        stepSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun stopSensors() {
        sensorManager?.unregisterListener(this)
    }

    @androidx.annotation.RequiresPermission(anyOf = [Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION])
    private fun startLocationUpdates() {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            app,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L).build()
            locationCallback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    if (!_state.value.isLive || _state.value.onPause) return
                    val newLocation = result.lastLocation ?: return
                    val tracks = _state.value.tracks.toMutableList()
                    if (tracks.isEmpty()) {
                        tracks.add(listOf(newLocation))
                    } else {
                        val lastTrack = tracks.last().toMutableList()
                        lastTrack.add(newLocation)
                        tracks[tracks.lastIndex] = lastTrack
                    }
                    val newDistance = calculateTotalDistance(tracks)
                    _state.value = _state.value.copy(
                        tracks = tracks,
                        distance = newDistance
                    )
                }
            }
            try {
                fusedLocationClient.requestLocationUpdates(
                    request,
                    locationCallback as LocationCallback,
                    null
                )
            } catch (e: SecurityException) {
                _state.value = _state.value.copy(errorMessage = "Location permission denied.")
            }
        } else {
            _state.value = _state.value.copy(errorMessage = "Location permissions are not granted.")
        }
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        locationCallback = null
    }

    private fun startTimer() {
        if (isTimerRunning) return
        isTimerRunning = true
        viewModelScope.launch {
            while (_state.value.isLive && !_state.value.onPause) {
                delay(1000)
                _state.value = _state.value.copy(
                    duration = _state.value.duration + 1.seconds
                )
            }
            isTimerRunning = false
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val steps = event.values[0].toInt()
            _state.value = _state.value.copy(steps = steps)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun calculateTotalDistance(tracks: List<List<Location>>): Double {
        var total = 0.0
        for (track in tracks) {
            for (i in 1 until track.size) {
                total += track[i - 1].distanceTo(track[i])
            }
        }
        return total
    }
}