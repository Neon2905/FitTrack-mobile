package com.fittrackapp.fittrack_mobile.presentation.register

import android.Manifest
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import androidx.annotation.RequiresPermission
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import com.google.android.gms.location.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityEntity
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.presentation.register.activity_live.sections.ResultSheet
import com.fittrackapp.fittrack_mobile.sheet.BottomSheetController
import com.fittrackapp.fittrack_mobile.utils.CalorieUtils.calculateCalories
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.utils.predictActivityType
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import java.util.Date
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.DurationUnit
import kotlin.time.toDuration


@HiltViewModel
class RegisterLiveActivityViewModel @Inject constructor(
    private val app: Application,
    private val activityDao: ActivityDao
) : ViewModel(), SensorEventListener {
    private val _state = MutableStateFlow(
        RegisterLiveActivityViewState(
            targetType = "distance",
            targetValue = getInitialTargetValue("distance")
        )
    )
    val state = _state.asStateFlow()

    private fun getInitialTargetValue(targetType: String): Double = when (targetType) {
        "distance" -> 1000.0 // meters
        "steps" -> 500.0
        "duration" -> 5.0 // minutes
        "calorie" -> 100.0
        else -> 1.0
    }

    private var sensorManager: SensorManager? = null
    private var stepSensor: Sensor? = null

    private var isTimerRunning = false
    private var locationCallback: LocationCallback? = null

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(app)

    val locationFlow: kotlinx.coroutines.flow.Flow<Location>

    init {
        sensorManager = app.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        locationFlow = callbackFlow<Location> {
            val callback = object : LocationCallback() {
                override fun onLocationResult(result: LocationResult) {
                    result.lastLocation?.let { trySend(it).isSuccess }
                }
            }
            val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L).build()
            try {
                fusedLocationClient.requestLocationUpdates(request, callback, null)
                awaitClose { fusedLocationClient.removeLocationUpdates(callback) }
            } catch (e: SecurityException) {
                close(e)
            }
        }

        viewModelScope.launch {
            fetchInitialLocation()
        }
    }

    suspend fun fetchInitialLocation(): Location? {
        val hasFineLocation = ContextCompat.checkSelfPermission(
            app,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            app,
            ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            return suspendCancellableCoroutine { cont ->
                Log.i("RegisterLiveActivityViewModel", "Fetching initial location...")
                fusedLocationClient.lastLocation
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val location = task.result
                            Log.i(
                                "RegisterLiveActivityViewModel",
                                "Fetched initial location: $location"
                            )
                            if (location != null) {
                                _state.value = _state.value.copy(currentLocation = location)
                            }
                            cont.resume(location)
                        } else {
                            cont.resumeWithException(
                                task.exception ?: Exception("Unknown error fetching location")
                            )
                        }
                    }
            }
        }
        return null
    }


    fun onTargetTypeChange(newType: String) {
        _state.value = _state.value.copy(
            targetType = newType,
            targetValue = getInitialTargetValue(newType)
        )
    }

    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    fun start() {
        if (!_state.value.isLive) {
            // First start: reset state and add first track
            _state.value = _state.value.copy(
                isLive = true,
                onPause = false,
                startTime = Date()
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
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
    }

    fun reset() {
        _state.value =
            RegisterLiveActivityViewState(
                targetValue = _state.value.targetValue,
                targetType = _state.value.targetType
            )
    }

    suspend fun save() {
        _state.update {
            it.copy(
                isSaving = true,
            )
        }
        val activityEntity =
            ActivityEntity(
                type = _state.value.activityType,
                startTime = _state.value.startTime ?: Date(),
                endTime = Date(),
                duration = _state.value.duration.inWholeMilliseconds,
                distance = _state.value.distance/1000,
                caloriesBurned = _state.value.calories,
                steps = _state.value.steps
            )

        activityDao.add(activityEntity).also {
            _state.update {
                it.copy(
                    isSaving = false,
                    showResultModal = false
                )
            }
            Toast.show(
                "Activity saved successfully!",
            )
            reset()
        }
    }

    fun showResultModal() {
        //
        // State listener cannot see if state is originally true.
        // So it needs to update it first to false, then to true.
        viewModelScope.launch {
            _state.update {
                it.copy(
                    showResultModal = false,
                )
            }
            delay(100) // Small delay to ensure state is updated
            _state.update {
                it.copy(
                    showResultModal = true,
                )
            }
        }
    }

    fun onCompleteOrStopped() {
        val progress = getCurrentValue() / _state.value.targetValue
        val isTargetReached = progress >= 1.0
        val isStoppedWithProgress = !isTargetReached && progress >= 0.1

        if (isTargetReached || isStoppedWithProgress) {
            // Update state to show result bottom sheet modal
            showResultModal()
        } else {
            reset()
        }

        stopSensors()
        stopLocationUpdates()
        countDownTimer?.cancel()
        isTimerRunning = false
        _state.value = _state.value.copy(isLive = false, onPause = false)
    }

    private fun getTargetDelta(): Double {
        return when (_state.value.targetType) {
            "distance" -> 250.0 // meters
            "steps" -> 250.0
            "duration" -> 5.0 // minutes
            "calorie" -> 50.0
            else -> 1.0
        }
    }

    fun increaseTargetValue() {
        val delta: Double = getTargetDelta()
        _state.update {
            it.copy(
                targetValue = _state.value.targetValue + delta
            )
        }
    }

    fun decreaseTargetValue() {
        val delta: Double = getTargetDelta()
        _state.update {
            it.copy(
                targetValue =
                    if (_state.value.targetValue > delta)
                        _state.value.targetValue - delta
                    else
                        _state.value.targetValue
            )
        }
    }

    private fun startSensors() {
        stepSensor?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    private fun stopSensors() {
        sensorManager?.unregisterListener(this)
    }

    fun getCurrentValue() = when (_state.value.targetType) {
        "distance" -> _state.value.distance
        "steps" -> _state.value.steps.toDouble()
        "duration" -> _state.value.duration.inWholeSeconds.toDouble() / 60.0
        "calorie" -> _state.value.calories
        else -> 0.0
    }

    private var countDownTimer: CountDownTimer? = null

    private fun startTimer() {
        if (isTimerRunning) return
        isTimerRunning = true

        countDownTimer =
            object : CountDownTimer(Long.MAX_VALUE, 500L) {
                var lastSteps = 0
                var lastDistance = 0.0
                var lastDuration = 0L

                override fun onTick(millisUntilFinished: Long) {
                    if (!_state.value.isLive || _state.value.onPause) {
                        cancel()
                        isTimerRunning = false
                        return
                    }

                    val deltaDistance = _state.value.distance - lastDistance
                    val deltaSteps = _state.value.steps - lastSteps

                    val deltaCalorie = calculateCalories(
                        deltaSteps,
                        deltaDistance / 1000,
                        0.5.toDuration(DurationUnit.SECONDS)
                    )
                    _state.value = _state.value.copy(
                        duration = _state.value.duration + 500L.milliseconds,
                        calories = (_state.value.calories + deltaCalorie)
                    )
                    if (getCurrentValue() >= _state.value.targetValue) {
                        onCompleteOrStopped()
                        _state.value = _state.value.copy(isTargetReached = true)
                        cancel()
                        isTimerRunning = false
                        return
                    }

                    lastSteps = _state.value.steps
                    lastDistance = _state.value.distance
                    lastDuration = _state.value.duration.inWholeSeconds
                }

                override fun onFinish() {
                    isTimerRunning = false
                }
            }.start()
    }

    /** IMPORTANT: If the app crashes when restarting timer, it's not my app that failed. Disable 'Live Edit' option in Android Studio.
    It's still in beta and causes issues with long-running tasks like timers.**/
    @RequiresPermission(anyOf = [ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION])
    private fun startLocationUpdates() {
        // Always stop previous updates before starting new ones
        stopLocationUpdates()

        val hasFineLocation = ContextCompat.checkSelfPermission(
            app,
            ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasCoarseLocation = ContextCompat.checkSelfPermission(
            app,
            ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasFineLocation || hasCoarseLocation) {
            val request =
                LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 2000L).build()
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
            _state.value =
                _state.value.copy(errorMessage = "Location permissions are not granted.")
        }
    }

    private fun stopLocationUpdates() {
        locationCallback?.let { fusedLocationClient.removeLocationUpdates(it) }
        locationCallback = null
    }

    private var baseSteps: Int? = null

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_STEP_COUNTER) {
            val currentSteps = event.values[0].toInt()
            if (baseSteps == null) baseSteps = currentSteps
            val sessionSteps = currentSteps - (baseSteps ?: 0)
            _state.value = _state.value.copy(steps = sessionSteps)
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