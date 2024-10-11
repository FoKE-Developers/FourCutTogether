package com.foke.together.presenter.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foke.together.util.AppPolicy
import com.foke.together.util.ImageFileUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

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

    fun downloadImage() {
        val imageUri = getFinalSingleImageUri()
        val imageBitmap = ImageFileUtil.getBitmapFromUri(context, imageUri)
        viewModelScope.launch {
            ImageFileUtil.saveBitmapToStorage(
                context,
                imageBitmap,
                AppPolicy.SINGLE_ROW_FINAL_IMAGE_NAME
            )
        }
    }
}