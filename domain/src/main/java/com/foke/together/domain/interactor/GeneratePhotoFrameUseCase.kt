package com.foke.together.domain.interactor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.ImageRepositoryInterface
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GeneratePhotoFrameUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageRepositoryInterface: ImageRepositoryInterface
){
    fun getCutFrameType(): CutFrameType = imageRepositoryInterface.getCutFrameType()
    suspend fun setCutFrameType(type: Int) = imageRepositoryInterface.setCutFrameType(type)

    fun getCapturedImageListUri(): List<Uri> = imageRepositoryInterface.getCachedImageUriList()
    suspend fun clearCapturedImageList() = imageRepositoryInterface.clearCacheDir()
    suspend fun saveGraphicsLayerImage(image: Bitmap, fileName: String) = imageRepositoryInterface.cachingImage(image, fileName)
    suspend fun saveFinalImage(image: Bitmap, fileName: String) = imageRepositoryInterface.saveToStorage(image, fileName)
}