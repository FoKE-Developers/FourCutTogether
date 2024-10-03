package com.foke.together.domain.input.di

import com.foke.together.domain.input.GetCameraSourceTypeInterface
import com.foke.together.domain.input.SetCameraSourceTypeInterface
import com.foke.together.domain.interactor.GetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.SetCameraSourceTypeUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetCameraSourceTypeUseCase(
        getCameraSourceTypeUseCase: GetCameraSourceTypeUseCase
    ): GetCameraSourceTypeInterface

    @Binds
    abstract fun bindSetCameraSourceTypeUseCase(
        setCameraSourceTypeUseCase: SetCameraSourceTypeUseCase
    ): SetCameraSourceTypeInterface
}