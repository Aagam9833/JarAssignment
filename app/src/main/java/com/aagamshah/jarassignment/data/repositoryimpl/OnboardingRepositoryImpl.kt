package com.aagamshah.jarassignment.data.repositoryimpl

import com.aagamshah.jarassignment.common.Resource
import com.aagamshah.jarassignment.data.api.OnboardingApiService
import com.aagamshah.jarassignment.data.dto.toDomain
import com.aagamshah.jarassignment.data.response.OnboardingApiResponse
import com.aagamshah.jarassignment.domain.model.OnboardingModel
import com.aagamshah.jarassignment.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val apiService: OnboardingApiService
) : OnboardingRepository {

    override suspend fun getOnboardingData(): Resource<OnboardingModel> {
        return try {
            val response: OnboardingApiResponse = apiService.getOnboardingData()

            if (response.success) {
                Resource.Success(
                    data = response.toDomain()
                )
            } else {
                Resource.Error(
                    message = "API call failed with status: success=false."
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(
                message = "Network error occurred or failed to process response: ${e.localizedMessage ?: "Unknown network failure"}"
            )
        }
    }
}