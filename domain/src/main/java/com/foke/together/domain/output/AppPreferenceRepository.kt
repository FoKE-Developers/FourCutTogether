package com.foke.together.domain.output

import kotlinx.coroutines.flow.Flow

interface AppPreferenceRepository {
    fun getSampleData(): Flow<SampleData>
    suspend fun setSampleData(data: SampleData)
    suspend fun clearAll()
}

data class SampleData (
    val id: String,
    val title: String,
    val description: String,
)