package com.foke.together.external.repository

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.foke.together.domain.output.ExternalCameraRepositoryInterface
import com.foke.together.external.network.ExternalCameraDataSource
import javax.inject.Inject

class ExternalCameraRepository @Inject constructor(
    private val externalCameraDataSource: ExternalCameraDataSource
): ExternalCameraRepositoryInterface {
    override fun setCameraSourceUrl(url: String) {
        externalCameraDataSource.setBaseUrl(url)
    }

    override suspend fun capture(): Result<Bitmap> {
        externalCameraDataSource.captureTest()
        return Result.failure(Exception())
    }

    override fun getPreviewUrl() =
        externalCameraDataSource.getPreviewUrl()

    companion object {
        private val TAG = ExternalCameraRepository::class.java.simpleName
    }
}