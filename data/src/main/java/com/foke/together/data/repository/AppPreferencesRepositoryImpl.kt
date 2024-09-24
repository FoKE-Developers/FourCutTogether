package com.foke.together.data.repository

import androidx.datastore.core.DataStore
import com.foke.together.AppPreferences
import com.foke.together.domain.output.AppPreferenceRepository
import com.foke.together.domain.output.SampleData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// TODO: datasource -> local / remote 기준으로 module 구분하는게 어떨지?
class AppPreferencesRepositoryImpl @Inject constructor(
    private val appPreferences: DataStore<AppPreferences>
): AppPreferenceRepository {
    private val appPreferencesFlow: Flow<AppPreferences> = appPreferences.data

    override fun getSampleData(): Flow<SampleData> =
        appPreferencesFlow.map {
            SampleData(it.sampleId, it.sampleTitle, it.sampleDescription)
        }

    override suspend fun setSampleData(data: SampleData) {
        appPreferences.updateData {
            it.toBuilder()
                .setSampleId(data.id)
                .setSampleTitle(data.title)
                .setSampleDescription(data.description)
                .build()
        }
    }

    override suspend fun clearAll() {
        appPreferences.updateData {
            it.toBuilder().clear().build()
        }
    }
}