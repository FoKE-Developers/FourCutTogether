package com.foke.together.domain.output

import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import com.foke.together.domain.interactor.entity.CutFrameType
import kotlinx.coroutines.flow.Flow

interface ImageRepositoryInterface {
    fun getCutFrameType(): CutFrameType
    suspend fun setCutFrameType(type: Int)
    // 촬영한 사진들 모음
    suspend fun cachingImage(image: Bitmap, fileName: String) : Uri
    fun getCachedImageUriList() : List<Uri>
    suspend fun clearCacheDir()

    // 완성된 프레임 모음
    suspend fun saveToStorage(image: Bitmap, fileName :String) : Uri
    fun getUriToBitmap(imageUri: Uri): Bitmap
}