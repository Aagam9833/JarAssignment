package com.aagamshah.jarassignment.data.api

import com.aagamshah.jarassignment.common.ApiConstants
import com.aagamshah.jarassignment.data.response.OnboardingApiResponse
import retrofit2.http.GET

interface OnboardingApiService {

    @GET(ApiConstants.ONBOARDING_ENDPOINT)
    suspend fun getOnboardingData(): OnboardingApiResponse
}
