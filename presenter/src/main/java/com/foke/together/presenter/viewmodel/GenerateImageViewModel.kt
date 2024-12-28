package com.foke.together.presenter.viewmodel

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase
): ViewModel() {
    val cutFrame: CutFrame = getCurrentSessionUseCase()?.cutFrame ?: run { throw Exception("invalid cut frame") }
    val imageUri = generatePhotoFrameUseCase.getCapturedImageListUri()

    suspend fun generateImage1(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalCachedImageUri = generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME)
        AppLog.d("GenerateImageViewModel", "generateTwoRowImage" ,"twoRow: $finalCachedImageUri")
    }

    suspend fun generateImage2(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalExternalImageUri = generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, AppPolicy.TWO_ROW_FINAL_IMAGE_NAME)
        AppLog.d("GenerateImageViewModel", "generateTwoRowImage" ,"twoRow: $finalExternalImageUri")
    }
}