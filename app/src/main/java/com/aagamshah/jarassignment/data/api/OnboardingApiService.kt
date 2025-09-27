package com.aagamshah.jarassignment.data.api

import com.aagamshah.jarassignment.data.dto.OnboardingApiResponse
import retrofit2.http.GET

interface OnboardingApiService {
    @GET("_assets/shared/education-metadata.json")
    suspend fun getEducationMetadata(): OnboardingApiResponse
}
