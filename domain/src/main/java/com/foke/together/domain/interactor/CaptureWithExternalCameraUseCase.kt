package com.foke.together.domain.interactor

import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.util.AppLog
import javax.inject.Inject

class CaptureWithExternalCameraUseCase @Inject constructor(
    private val externalCameraRepository: ExternalCameraRepositoryInterface
) {
    suspend operator fun invoke(): Result<Unit> {
        externalCameraRepository.capture()
            .onSuccess {
                // TODO: save Bitmap to internal storage
                AppLog.i(TAG, "capture", "success: $it")

                // TODO: implement file save code here ...

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