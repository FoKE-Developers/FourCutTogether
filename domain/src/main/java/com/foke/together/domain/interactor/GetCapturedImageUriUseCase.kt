package com.foke.together.domain.interactor

import android.net.Uri
import com.foke.together.domain.output.InternalCameraRepositoryInterface
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCapturedImageUriUseCase @Inject constructor(
    private val internalCameraRepository: InternalCameraRepositoryInterface
) {
    operator fun invoke() : Flow<Uri?> = internalCameraRepository.getCapturedImageUri()
}