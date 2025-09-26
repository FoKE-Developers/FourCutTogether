package com.foke.together.domain.interactor

import androidx.camera.core.CameraSelector
import com.foke.together.domain.output.AppPreferenceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetInternalCameraLensFacingUseCase @Inject constructor(
    private val appPreferenceRepository: AppPreferenceInterface
) {
    operator fun invoke() : Flow<Int> = appPreferenceRepository.getInternalCameraLensFacing()
}