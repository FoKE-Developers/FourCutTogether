package com.foke.together.domain.interactor

import android.content.Context
import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.util.AppLog
import com.foke.together.util.ImageFileUtil
import com.foke.together.util.TimeUtil
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

// TODO: CameraScreen에서  filepath를 TimeUtil.getCurrentTimeSec() 로 전달
//  filepath를 ScameraScreen -> GenerateScreen -> ShareScreen 로 전달
//  추후에는 filepath를 presenter에서 관리하지 않도록 해야 할듯
class CaptureWithExternalCameraUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val externalCameraRepository: ExternalCameraRepositoryInterface
) {
    suspend operator fun invoke(filepath: String): Result<Unit> {
        externalCameraRepository.capture()
            .onSuccess {
                // TODO: create file module in phase4
                ImageFileUtil.saveBitmap(context, it, filepath, TimeUtil.getCurrentTimeMillis())
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