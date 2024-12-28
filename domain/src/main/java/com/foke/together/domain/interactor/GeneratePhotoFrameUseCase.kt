package com.foke.together.domain.interactor

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppPolicy
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@Deprecated("Not in use")
class GeneratePhotoFrameUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    private val imageRepositoryInterface: ImageRepositoryInterface
){
    // 촬영한 이미지 리스트 관리
    @Deprecated("Not in use")
    fun getCapturedImageListUri(): List<Uri> = imageRepositoryInterface.getCachedImageUriList()
    @Deprecated("Not in use")
    suspend fun clearCapturedImageList() = imageRepositoryInterface.clearCacheDir()


    // 화면 요소를 이미지로 저장
    @Deprecated("Not in use")
    suspend fun saveGraphicsLayerImage(image: Bitmap, fileName: String) = imageRepositoryInterface.cachingImage(image, fileName)
    @Deprecated("Not in use")
    suspend fun saveFinalImage(image: Bitmap, fileName: String) = imageRepositoryInterface.saveToStorage(image, fileName)


    // 최종 이미지 URL 가져오기
    @Deprecated("Not in use")
    fun getFinalSingleImageUri(): Uri {
        var finalSingleImageUri: Uri = Uri.EMPTY
        context.cacheDir.listFiles().forEach {
                file -> if (file.name.contains("${AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME}")) { finalSingleImageUri = Uri.fromFile(file) }
        }
        return finalSingleImageUri
    }
    @Deprecated("Not in use")
    fun getFinalTwoImageUri(): Uri {
        var finalTwoImageUri: Uri = Uri.EMPTY
        context.cacheDir.listFiles().forEach {
                file -> if (file.name.contains("${AppPolicy.TWO_ROW_FINAL_IMAGE_NAME}")) { finalTwoImageUri = Uri.fromFile(file) }
        }
        return finalTwoImageUri
    }
}