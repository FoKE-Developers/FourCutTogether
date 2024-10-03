package com.foke.together.data.datasource.local.datastore.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStoreFile
import com.foke.together.AppPreferences
import com.foke.together.data.datasource.local.datastore.AppPreferencesSerializer
import com.foke.together.util.di.ApplicationScope
import com.foke.together.util.di.IODispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PreferencesModule {
    @Singleton
    @Provides
    fun provideAppPreferencesDataStore(
        @ApplicationContext context: Context,
        @ApplicationScope scope: CoroutineScope,
        @IODispatcher ioDispatcher: CoroutineDispatcher,
        appPreferencesSerializer: AppPreferencesSerializer
    ): DataStore<AppPreferences> = DataStoreFactory.create(
        serializer = appPreferencesSerializer,
        scope = CoroutineScope(scope.coroutineContext + ioDispatcher),
        produceFile = { context.dataStoreFile("app_preferences.pb") }
    )
}