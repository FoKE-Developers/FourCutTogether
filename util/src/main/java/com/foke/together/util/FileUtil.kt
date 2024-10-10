package com.foke.together.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

object FileUtil {
    fun readCachedQRCodeImage(context: Context, key: String): Bitmap? {
        val qrImageFile = File("${context.cacheDir}/$key/${AppPolicy.DEFAULT_QR_CODE_IMAGE_NAME}.jpg")
        var qrImage: Bitmap? = null

        if (qrImageFile.exists()) {
            qrImage = BitmapFactory.decodeFile(qrImageFile.absolutePath)
        }
        return qrImage
    }

    fun createDir(context: Context, key: String) {
        val dir = File("${context.cacheDir}/$key")
        if(!dir.exists()) {
            dir.mkdirs()
        }
    }
}