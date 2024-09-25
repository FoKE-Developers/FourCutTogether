package com.foke.together.domain.input

import kotlinx.coroutines.flow.Flow

interface GetSampleDataInterface {
    operator fun invoke(): Flow<SampleUiData>
}

data class SampleUiData(
    val sampleText: String
)