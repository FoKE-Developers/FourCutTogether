package com.foke.together.data.repository.di

import com.foke.together.data.repository.AccountRepository
import com.foke.together.data.repository.AppPreferencesRepository
import com.foke.together.data.repository.RemoteRepository
import com.foke.together.domain.output.AccountRepositoryInterface
import com.foke.together.domain.output.AppPreferenceInterface
import com.foke.together.domain.output.RemoteRepositoryInterface
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
    abstract fun bindAppPreferenceRepository(appPreferenceRepository: AppPreferencesRepository): AppPreferenceInterface

    @Singleton
    @Binds
    abstract fun bindRemoteRepository(remoteRepository: RemoteRepository): RemoteRepositoryInterface

    @Singleton
    @Binds
    abstract fun bindAccountRepository(accountRepository: AccountRepository): AccountRepositoryInterface
}