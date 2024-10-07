package com.foke.together.domain.interactor

import com.foke.together.domain.interactor.entity.CutFrameSourceType
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetCutFrameSourceTypeUseCase @Inject constructor(
    private val appPreference: AppPreferenceInterface
) {
    operator fun invoke(): Flow<CutFrameSourceType> =
        appPreference.getCutFrameSourceType().map { it }
}