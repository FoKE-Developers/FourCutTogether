package com.foke.together.domain.output

import android.graphics.Bitmap

interface QRCodeRepositoryInterface {
    suspend fun generateQRCode(key: String, url: String): Result<Unit>
    suspend fun readQRCode(key: String): Result<Bitmap>
}