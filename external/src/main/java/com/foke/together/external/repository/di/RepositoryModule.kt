package com.foke.together.external.repository.di

import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.external.repository.ExternalCameraRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindAppPreferenceRepository(
        externalCameraRepository: ExternalCameraRepository
    ): ExternalCameraRepositoryInterface
}