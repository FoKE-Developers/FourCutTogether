package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.CameraSourceType
import com.foke.together.domain.interactor.entity.SampleData
import kotlinx.coroutines.flow.Flow

interface AppPreferenceInterface {
    // TODO: sample code. remove later
    fun getSampleData(): Flow<SampleData>
    suspend fun setSampleData(data: SampleData)

    fun getCameraSourceType(): Flow<CameraSourceType>
    suspend fun setCameraSourceType(type: CameraSourceType)

    suspend fun clearAll()
}