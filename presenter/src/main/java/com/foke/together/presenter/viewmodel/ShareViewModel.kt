package com.foke.together.presenter.viewmodel

import android.content.Context
import android.net.Uri
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class ShareViewModel @Inject constructor(
    @ApplicationContext private val context: Context
): ViewModel() {

    fun getFinalSingleImageUri(): Uri {
        var finalSingleImageUri: Uri = Uri.EMPTY
        context.cacheDir.listFiles().forEach {
                file -> if (file.name.contains("final_single_row.jpg")) { finalSingleImageUri = Uri.fromFile(file) }
        }
        return finalSingleImageUri
    }
    fun shareImage() {

    }
    fun printImage() {

    }

    fun downloadImage() {

    }
}