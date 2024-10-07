package com.foke.together.domain.output

import android.graphics.Bitmap

interface ExternalCameraRepositoryInterface {
    fun setCameraSourceUrl(url: String)
    suspend fun capture(): Result<Bitmap>
    fun getPreviewUrl(): String
}