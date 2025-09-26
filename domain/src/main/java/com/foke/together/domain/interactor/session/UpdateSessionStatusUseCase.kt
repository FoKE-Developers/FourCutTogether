package com.foke.together.domain.interactor.session

import android.graphics.Bitmap
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.entity.Status
import com.foke.together.domain.output.SessionRepositoryInterface
import javax.inject.Inject

class UpdateSessionStatusUseCase @Inject constructor(
    private val sessionRepository: SessionRepositoryInterface
) {
    operator fun invoke(status: Status) {
        sessionRepository.updateSession(status)
    }

    operator fun invoke(cutFrame: CutFrame) {
        sessionRepository.updateSession(cutFrame)
    }

    operator fun invoke(qrCodeBitmap: Bitmap) {
        sessionRepository.updateSession(qrCodeBitmap)
    }
}