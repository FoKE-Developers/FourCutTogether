package com.foke.together.domain.interactor

import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import javax.inject.Inject

class GetExternalCameraPreviewUrlUseCase @Inject constructor(
    private val externalCameraRepository: ExternalCameraRepositoryInterface
) {
    operator fun invoke() = externalCameraRepository.getPreviewUrl()
}