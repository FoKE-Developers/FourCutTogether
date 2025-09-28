package com.foke.together.domain.interactor.web

import com.foke.together.domain.output.RemoteRepositoryInterface
import com.foke.together.util.AppLog
import java.io.File
import javax.inject.Inject

// Download url
// https://4cuts.store/download/{user_name}/{key}
class UploadFileUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryInterface
) {
    suspend operator fun invoke(key: String, file: File): Result<Unit> {
        remoteRepository.getUploadUrl("$key.${file.extension}", file)
            .onSuccess { preSignedUrl ->
                AppLog.e(TAG, "invoke", "preSignedUrl: $preSignedUrl")
                remoteRepository.uploadFile(preSignedUrl, file)
                    .onSuccess {
                        return Result.success(Unit)
                    }
                    .onFailure {
                        AppLog.e(TAG, "invoke", "upload failed")
                        return Result.failure(Exception("cannot upload file: $it"))
                    }
            }
            .onFailure {
                return Result.failure(Exception("cannot get pre-signed url: $it"))
            }
        return Result.failure(Exception("Unknown error"))
    }

    companion object {
        private val TAG = UploadFileUseCase::class.java.simpleName
    }
}