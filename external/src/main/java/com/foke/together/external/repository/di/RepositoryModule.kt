package com.foke.together.external.repository.di

import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.external.repository.ExternalCameraRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppPreferenceRepository(
        externalCameraRepository: ExternalCameraRepository
    ): ExternalCameraRepositoryInterface
}