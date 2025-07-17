package com.fittrackapp.fittrack_mobile.presentation.register

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.data.local.dao.ActivityDao
import com.fittrackapp.fittrack_mobile.data.local.entity.ActivityType
import com.fittrackapp.fittrack_mobile.domain.model.Activity
import com.fittrackapp.fittrack_mobile.utils.CalorieUtils.calculateCalories
import com.fittrackapp.fittrack_mobile.utils.Toast
import com.fittrackapp.fittrack_mobile.utils.asEntity
import com.fittrackapp.fittrack_mobile.utils.round
import com.fittrackapp.fittrack_mobile.utils.tryCastToDouble
import com.fittrackapp.fittrack_mobile.utils.tryCastToInt
import com.google.android.gms.location.SleepSegmentEvent
import com.google.android.gms.tasks.Tasks.await
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.Date
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class LogActivityViewModel @Inject constructor(
    private val activityDao: ActivityDao
) : ViewModel() {

    private val _state = MutableStateFlow(
        LogActivityViewState(
            activityType = ActivityType.WALKING,
            startTime = Date(),
            duration = 0.minutes,
        )
    )
    val state = _state.asStateFlow()

    // <editor-fold desc="State Management">
    fun onActivityTypeSelected(type: ActivityType) {
        _state.update {
            it.copy(activityType = type)
        }
    }

    fun onStartTimeChanged(startTime: Date) {
        _state.update {
            it.copy(startTime = startTime)
        }
    }

    fun onDurationChanged(duration: String) {
        if (duration.tryCastToInt() == false)
            return
        _state.update {
            it.copy(duration = duration.toInt().minutes)
        }
    }

    fun onDistanceChanged(distance: String) {
        val normalizedDistance =
            if (distance.endsWith(".")) {
                distance.dropLast(1)
            } else {
                distance
            }

        if (normalizedDistance.tryCastToDouble() == false)
            return

        _state.update {
            it.copy(distance = normalizedDistance.toDouble())
        }
    }

    fun onStepsChanged(steps: String) {
        if (steps.tryCastToInt() == false)
            return
        _state.update {
            it.copy(steps = steps.toInt())
        }
    }

    fun onRepsChanged(reps: String) {
        if (reps.tryCastToInt() == false)
            return
        _state.update {
            it.copy(reps = reps.toInt())
        }
    }

    fun onLiftWeightChanged(liftWeight: String) {
        if (liftWeight.tryCastToDouble() == false)
            return
        _state.update {
            it.copy(liftWeight = liftWeight.toDouble())
        }
    }

    fun onLapsChanged(laps: String) {
        if (laps.tryCastToInt() == false)
            return
        _state.update {
            it.copy(laps = laps.toInt())
        }
    }

    fun onPoolLengthChanged(poolLength: String) {
        if (poolLength.tryCastToDouble() == false)
            return
        _state.update {
            it.copy(poolLength = poolLength.toDouble())
        }
    }

    fun onSetsChanged(sets: String) {
        if (sets.tryCastToInt() == false)
            return
        _state.update {
            it.copy(sets = sets.toInt())
        }
    }
    // </editor-fold>

    fun save() {
        viewModelScope.launch {
            _state.update { it.copy(isSaving = true) }
            val calories = calculateCalories(
                activityType = _state.value.activityType,
                duration = _state.value.duration,
                distanceKm = _state.value.distance,
                reps = _state.value.reps,
                sets = _state.value.sets,
                liftWeightKg = _state.value.liftWeight,
                laps = _state.value.laps,
                poolLength = _state.value.poolLength
            )

            val activity = Activity(
                type = _state.value.activityType.name,
                startTime = Date.from(_state.value.startTime.toInstant()),
                endTime = Date(_state.value.startTime.time + _state.value.duration.inWholeMilliseconds),
                steps = _state.value.steps,
                duration = _state.value.duration.inWholeMilliseconds,
                distance = _state.value.distance,
                reps = _state.value.reps,
                calories = calories
            )
            try {
                activityDao.add(activity.asEntity())

                Toast.show("You just burned ${calories.round()} calories!")
            } catch (e: Exception) {
                e.printStackTrace()
                Toast.show("Failed to save activity: ${e.message}")
            } finally {
                _state.update { it.copy(isSaving = false) }
            }
        }
    }
}