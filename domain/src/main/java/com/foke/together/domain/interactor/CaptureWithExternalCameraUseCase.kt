package com.foke.together.domain.interactor

import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppLog
import javax.inject.Inject

class CaptureWithExternalCameraUseCase @Inject constructor(
    private val externalCameraRepository: ExternalCameraRepositoryInterface,
    private val imageRepository: ImageRepositoryInterface
) {
    suspend operator fun invoke(fileName: String): Result<Unit> {
        externalCameraRepository.capture()
            .onSuccess {
                AppLog.i(TAG, "capture", "success: $it")
                imageRepository.cachingImage(it, fileName)
                return Result.success(Unit)
            }
            .onFailure {
                // TODO: handle network error
                AppLog.i(TAG, "capture", "failure: $it")
                return Result.failure(it)
            }
        return Result.failure(Exception("Unknown error"))
    }

    companion object {
        private val TAG = CaptureWithExternalCameraUseCase::class.java.simpleName
    }
}