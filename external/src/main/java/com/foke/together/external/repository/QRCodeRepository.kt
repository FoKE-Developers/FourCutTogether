package com.foke.together.external.repository

import android.content.Context
import com.foke.together.domain.output.QRCodeRepositoryInterface
import com.foke.together.external.qrcode.QRCodeGenerator
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class QRCodeRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val qrCodeGenerator: QRCodeGenerator
): QRCodeRepositoryInterface {
    override suspend fun generateQRCode(key: String, url: String): Result<Unit> {
        return qrCodeGenerator.generate(context, key, url)
    }

    override suspend fun readQRCode(key: String) =
        qrCodeGenerator.readFile(context, key)

    companion object {
        private val TAG = QRCodeRepository::class.java.simpleName
    }
}