package com.foke.together.presenter.viewmodel

import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.domain.interactor.GenerateImageFromViewUseCase
import com.foke.together.domain.interactor.GeneratePhotoFrameUseCaseV1
import com.foke.together.domain.interactor.GetCameraSourceTypeUseCase
import com.foke.together.domain.interactor.entity.CutFrame
import com.foke.together.domain.interactor.session.GetCurrentSessionUseCase
import com.foke.together.util.AppLog
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GenerateImageViewModel @Inject constructor(
    private val getCameraSourceTypeUseCase : GetCameraSourceTypeUseCase,
    private val getCurrentSessionUseCase: GetCurrentSessionUseCase,
    private val generateImageFromViewUseCase: GenerateImageFromViewUseCase,
    private val generatePhotoFrameUseCaseV1: GeneratePhotoFrameUseCaseV1,
): ViewModel() {
    val cutFrame: CutFrame = getCurrentSessionUseCase()?.cutFrame ?: run { throw Exception("invalid cut frame") }

    val imageUri = getCameraSourceTypeUseCase().map{ sourceType ->
        generatePhotoFrameUseCaseV1.getCapturedImageListUri(sourceType)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = listOf()
    )

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