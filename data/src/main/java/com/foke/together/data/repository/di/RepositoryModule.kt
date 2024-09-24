package com.foke.together.data.repository.di

import com.foke.together.data.repository.AppPreferencesRepositoryImpl
import com.foke.together.domain.output.AppPreferenceRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindAppPreferenceRepository(appPreferenceRepository: AppPreferencesRepositoryImpl): AppPreferenceRepository
}