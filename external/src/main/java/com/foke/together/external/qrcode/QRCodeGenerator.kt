package com.foke.together.external.qrcode

import android.content.Context
import android.graphics.Bitmap
import com.foke.together.util.AppPolicy
import com.foke.together.util.FileUtil
import com.foke.together.util.ImageFileUtil
import qrcode.QRCode
import qrcode.color.Colors
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
                .withMargin(20)
                .withInnerSpacing(0)
                // TODO: 아래 속성이 없어서 QR코드가 안보였던건데 그 전에는 왜 제대로 동작했는지?
                .withColor(Colors.BLACK)
                .withBackgroundColor(Colors.WHITE)
                // TODO: check customize qrcode
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