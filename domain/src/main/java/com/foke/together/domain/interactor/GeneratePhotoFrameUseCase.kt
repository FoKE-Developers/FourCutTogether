package com.foke.together.domain.interactor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppPolicy
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

    fun getFinalSingleImageUri(): Uri {
        var finalSingleImageUri: Uri = Uri.EMPTY
        context.cacheDir.listFiles().forEach {
                file -> if (file.name.contains("${AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME}")) { finalSingleImageUri = Uri.fromFile(file) }
        }
        return finalSingleImageUri
    }

    fun getFinalTwoImageUri(): Uri {
        var finalTwoImageUri: Uri = Uri.EMPTY
        context.cacheDir.listFiles().forEach {
                file -> if (file.name.contains("${AppPolicy.TWO_ROW_FINAL_IMAGE_NAME}")) { finalTwoImageUri = Uri.fromFile(file) }
        }
        return finalTwoImageUri
    }
}