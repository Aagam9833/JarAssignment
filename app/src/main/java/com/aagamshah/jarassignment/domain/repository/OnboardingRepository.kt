package com.aagamshah.jarassignment.domain.repository

import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.domain.model.OnboardingModel

interface OnboardingRepository {
    suspend fun getOnboardingData(): Resource<OnboardingModel>
}
