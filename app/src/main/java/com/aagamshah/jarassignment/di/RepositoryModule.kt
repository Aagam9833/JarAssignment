package com.aagamshah.jarassignment.di

import com.aagamshah.jarassignment.data.repositoryimpl.OnboardingRepositoryImpl
import com.aagamshah.jarassignment.domain.repository.OnboardingRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindOnboardingRepository(onboardingRepositoryImpl: OnboardingRepositoryImpl): OnboardingRepository

}