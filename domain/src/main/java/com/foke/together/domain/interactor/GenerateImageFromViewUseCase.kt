package com.foke.together.domain.interactor

import android.net.Uri
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.layer.GraphicsLayer
import com.foke.together.domain.output.ImageRepositoryInterface
import com.foke.together.util.AppLog
import com.foke.together.util.AppPolicy
import javax.inject.Inject

class GenerateImageFromViewUseCase @Inject constructor(
    private val imageRepositoryInterface: ImageRepositoryInterface
) {
    // TODO: UI -> UC -> UI 흐름으로 만들어보기
    suspend operator fun invoke(graphicsLayer: GraphicsLayer, filename: String? = null, isCutFrameForPrint: Boolean = false): Uri {
        val bitmap = graphicsLayer.toImageBitmap().asAndroidBitmap()
        val finalCachedImageUri = imageRepositoryInterface.cachingImage(
            bitmap,
            filename ?: run {
                if (isCutFrameForPrint) AppPolicy.TWO_ROW_FINAL_IMAGE_NAME else AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME
            }
        )
        AppLog.d(TAG, "invoke" ,"finalCachedImageUri: $finalCachedImageUri")
        return finalCachedImageUri
    }

    companion object {
        private val TAG = GenerateImageFromViewUseCase::class.java.simpleName
    }
}