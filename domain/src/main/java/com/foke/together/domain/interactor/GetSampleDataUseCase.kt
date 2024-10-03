package com.foke.together.domain.interactor

import com.foke.together.domain.input.GetSampleDataInterface
import com.foke.together.domain.input.SampleUiData
import com.foke.together.domain.output.AppPreferenceInterface
import com.foke.together.util.AppLog
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

// TODO: sample code. remove later
class GetSampleDataUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
): GetSampleDataInterface {
    override fun invoke(): Flow<SampleUiData> =
        appPreference.getSampleData().map {
            AppLog.i(TAG, "", "id: ${it.id}")
            SampleUiData("title: ${it.title} / description: ${it.description}")
        }

    companion object {
        private val TAG = GetSampleDataUseCase::class.java.simpleName
    }
}