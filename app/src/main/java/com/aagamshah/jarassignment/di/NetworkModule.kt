package com.aagamshah.jarassignment.di

import com.aagamshah.jarassignment.common.ApiConstants
import com.aagamshah.jarassignment.data.api.OnboardingApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(ApiConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOnboardingApiService(retrofit: Retrofit): OnboardingApiService {
        return retrofit.create(OnboardingApiService::class.java)
    }

}