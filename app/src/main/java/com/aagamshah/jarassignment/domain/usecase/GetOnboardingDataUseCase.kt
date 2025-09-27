package com.aagamshah.jarassignment.domain.usecase

import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.domain.model.OnboardingModel
import com.aagamshah.jarassignment.domain.repository.OnboardingRepository
import javax.inject.Inject

class GetOnboardingDataUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    suspend operator fun invoke(): Resource<OnboardingModel> {
        return repository.getOnboardingData()
    }
}
