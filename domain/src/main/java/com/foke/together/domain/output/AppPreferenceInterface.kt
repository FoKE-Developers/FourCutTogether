package com.foke.together.domain.output

import com.foke.together.domain.interactor.SampleData
import com.foke.together.domain.interactor.CameraSourceType
import kotlinx.coroutines.flow.Flow

interface AppPreferenceInterface {
    // TODO: sample code. remove later
    fun getSampleData(): Flow<SampleData>
    suspend fun setSampleData(data: SampleData)

    fun getCameraSourceType(): Flow<CameraSourceType>
    suspend fun setCameraSourceType(type: CameraSourceType)

    suspend fun clearAll()
}