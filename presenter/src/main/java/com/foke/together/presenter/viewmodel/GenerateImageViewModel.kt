package com.foke.together.presenter.viewmodel

import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import com.foke.together.domain.interactor.GenerateImageFromViewUseCase
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.util.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(
    getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val generateImageFromViewUseCase: GenerateImageFromViewUseCase,
    generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1,
): ViewModel() {
    val cutFrame: CutFrame = getCurrentSessionUseCase()?.cutFrame ?: run { throw Exception("invalid cut frame") }
    val imageUri = generatePhotoFrameUseCaseV1.getCapturedImageListUri()

    suspend fun generateImage(graphicsLayer: GraphicsLayer) {
        val finalCachedImageUri = generateImageFromViewUseCase(graphicsLayer, isCutFrameForPrint = false)
        AppLog.d(TAG, "generateImage" ,"finalCachedImageUri: $finalCachedImageUri")
    }

    suspend fun generateImageForPrint(graphicsLayer: GraphicsLayer) {
        val finalExternalImageUri = generateImageFromViewUseCase(graphicsLayer, isCutFrameForPrint = true)
        AppLog.d(TAG, "generateImageForPrint" ,"finalExternalImageUri: $finalExternalImageUri")
    }

    companion object {
        private val TAG = GenerateImageViewModel::class.java.simpleName
    }
}