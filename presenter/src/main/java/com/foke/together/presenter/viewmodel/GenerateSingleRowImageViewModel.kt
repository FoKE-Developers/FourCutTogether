package com.foke.together.presenter.viewmodel

import android.content.Context
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.core.net.toFile
import androidx.lifecycle.ViewModel
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCase
import com.foke.together.domain.interactor.GetQRCodeUseCase
import com.foke.together.domain.interactor.entity.CutFrameType
import com.foke.together.domain.interactor.web.SessionKeyUseCase
import com.foke.together.domain.interactor.web.UploadFileUseCase
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class GenerateSingleRowImageViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generatePhotoFrameUseCase: GeneratePhotoFrameUseCase,
    private val uploadFileUseCase: UploadFileUseCase,
    private val sessionKeyUseCase: SessionKeyUseCase,
    private val getQRCodeUseCase: GetQRCodeUseCase
): ViewModel() {
    val cutFrameType: CutFrameType = generatePhotoFrameUseCase.getCutFrameType()
    val imageUri = generatePhotoFrameUseCase.getCapturedImageListUri()

    suspend fun generateImage(graphicsLayer: GraphicsLayer) {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalCachedImageUri = generatePhotoFrameUseCase.saveGraphicsLayerImage(bitmap, AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME)
        val result = uploadFileUseCase(sessionKeyUseCase.getSessionKey(), finalCachedImageUri.toFile())
        AppLog.d("GenerateImageViewModel", "generateTwoRowImage" ,"twoRow: $finalCachedImageUri")
        AppLog.d("GenerateImageViewModel", "UploadFile" ,"result: $result")
    }
}