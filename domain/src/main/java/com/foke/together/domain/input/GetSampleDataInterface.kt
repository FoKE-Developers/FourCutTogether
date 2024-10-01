package com.foke.together.domain.input

import kotlinx.coroutines.flow.Flow

// TODO: sample code. remove later.
interface GetSampleDataInterface {
    operator fun invoke(): Flow<SampleUiData>
}

// TODO: sample code. remove later.
data class SampleUiData(
    val sampleText: String
)