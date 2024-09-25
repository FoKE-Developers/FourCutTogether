package com.foke.together.domain.input.di

import com.foke.together.domain.input.GetSampleDataInterface
import com.foke.together.domain.interactor.GetSampleDataUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class UseCaseModule {
    @Binds
    abstract fun bindGetSampleDataUseCase(getSampleUiDataUseCase: GetSampleDataUseCase): GetSampleDataInterface
}