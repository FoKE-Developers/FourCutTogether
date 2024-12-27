package com.foke.together.presenter.viewmodel

import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.entity.CutFrameTypeV1
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateSingleRowImageViewModel @Inject constructor(
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase,
): ViewModel() {
    val cutFrameType: CutFrameTypeV1 = generatePhotoFrameUseCase.getCutFrameType()
    val imageUri = generatePhotoFrameUseCase.getCapturedImageListUri()

    suspend fun generateImage(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalCachedImageUri = generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME)
        AppLog.d("GenerateImageViewModel", "generateTwoRowImage" ,"twoRow: $finalCachedImageUri")
    }
}