package com.foke.together.domain.interactor

import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetInternalCameraCaptureModeUseCase @Inject constructor(
    private val appPreferenceRepository: AppPreferenceInterface
) {
    operator fun invoke() : Flow<Int> = appPreferenceRepository.getInternalCameraCaptureMode()
}