package com.foke.together.external.qrcode

import android.content.Context
import android.graphics.Bitmap
import com.foke.together.util.AppPolicy
import com.foke.together.util.FileUtil
import com.foke.together.util.ImageFileUtil
import qrcode.QRCode
import javax.inject.Inject

class QRCodeGenerator @Inject constructor(
) {
    suspend fun generate(context: Context, key: String, data: String): Result<Unit> =
        runCatching {
            val squares = QRCode.ofSquares()
            // TODO: check customize qrcode
//        val circles = QRCode.ofCircles()
//        val roundedSquares = QRCode.ofRoundedSquares()

            val qrcode = squares
                .withInnerSpacing(0)
                // TODO: check customize qrcode
//            .withColor(Colors.BLACK)
//            .withRadius(20)
//            .withSize(25)
//            .withLogo(logo.readBytes(), 20, 20)
                .build(data)

            val qrImage = qrcode.render().nativeImage() as Bitmap

            // TODO: need to merge ImageFileUtil and FileUtil
            FileUtil.createDir(context, key)
            ImageFileUtil.cacheBitmap(context, qrImage, "$key/${AppPolicy.DEFAULT_QR_CODE_IMAGE_NAME}")
        }

    fun readFile(context: Context, key: String): Result<Bitmap> {
        FileUtil.readCachedQRCodeImage(context, key)?.run {
            return Result.success(this)
        }
        return Result.failure(Exception("cannot read file"))
    }
}