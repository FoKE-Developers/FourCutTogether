package com.foke.together.domain.interactor

import android.graphics.Bitmap
import com.foke.together.domain.output.QRCodeRepositoryInterface
import javax.inject.Inject

class GetQRCodeUseCase @Inject constructor(
    private val qrCodeRepository: QRCodeRepositoryInterface
){
    suspend operator fun invoke(key: String, url: String): Result<Bitmap> {
        qrCodeRepository.generateQRCode(key, url)
            .onSuccess {
                return qrCodeRepository.readQRCode(key)
            }
        return Result.failure(Exception("cannot generate qr code"))
    }
}