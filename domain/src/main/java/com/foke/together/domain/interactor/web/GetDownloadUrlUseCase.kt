package com.foke.together.domain.interactor.web

import com.foke.together.util.AppPolicy
import javax.inject.Inject

// Download url
// https://4cuts.store/download/{user_name}/{key}
class GetDownloadUrlUseCase @Inject constructor(
    private val getCurrentUserInformationUseCase: GetCurrentUserInformationUseCase
) {
    suspend operator fun invoke(key: String): Result<String> {
        getCurrentUserInformationUseCase()
            .onSuccess {
                "${AppPolicy.WEB_SERVER_URL}download/${it.name}/$key"
            }
        return Result.failure(Exception("Unknown error"))
    }
}