package com.fittrackapp.fittrack_mobile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fittrackapp.fittrack_mobile.domain.repository.ActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExperimentViewModel @Inject constructor(
    private val activityRepository: ActivityRepository
) : ViewModel() {

    fun fetch(){
        viewModelScope.launch{
            activityRepository.getAll()
        }
    }
}