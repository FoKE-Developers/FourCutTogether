package com.foke.together.domain.interactor.web

import com.foke.together.domain.interactor.entity.AccountData
import com.foke.together.domain.output.RemoteRepositoryInterface
import com.foke.together.util.AppLog
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryInterface,
) {
    suspend operator fun invoke(id: String, password: String, name: String): Result<Unit> {
        return remoteRepository.registerAccount(AccountData(id, password, name))
            .onSuccess {
                AppLog.i("RegisterUseCase", "init", "register success: ${it}")
            }.onFailure { ee ->
                AppLog.e("RegisterUseCase", "init", "error: ${ee}")
            }
    }
}