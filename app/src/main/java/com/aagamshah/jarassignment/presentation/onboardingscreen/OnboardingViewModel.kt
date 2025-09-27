package com.aagamshah.jarassignment.presentation.onboardingscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.domain.model.OnboardingModel
import com.aagamshah.jarassignment.domain.usecase.GetOnboardingDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val getOnboardingDataUseCase: GetOnboardingDataUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<Resource<OnboardingModel>>(Resource.Loading())
    val state: StateFlow<Resource<OnboardingModel>> = _state

    init {
        loadOnboardingData()
    }

    private fun loadOnboardingData() {
        viewModelScope.launch {
            _state.value = Resource.Loading(_state.value.data)

            val result = getOnboardingDataUseCase()

            when (result) {
                is Resource.Success -> {
                    _state.value = result
                    Log.d("TAGGED", "loadOnboardingData: ${result.data}")
                }

                is Resource.Error -> {
                    Log.d("TAGGED", "loadOnboardingData: ${result.message}")
                    _state.value = Resource.Error(
                        message = result.message ?: "An unexpected error occurred",
                        data = result.data
                    )
                }

                is Resource.Loading -> {
                    _state.value = result
                }
            }
        }
    }
}
