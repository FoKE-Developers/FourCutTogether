package com.foke.together.domain.interactor.entity

import android.graphics.Bitmap

data class SessionData (
    val sessionId: SessionId,
    val cutFrame: CutFrame? = null,
    val qrCodeBitmap: Bitmap? = null,
    val status: Status
)

enum class Status {
    INIT,
    SELECT_FRAME,
    CAPTURE,
    GENERATE_PHOTO,
    SHARE
}