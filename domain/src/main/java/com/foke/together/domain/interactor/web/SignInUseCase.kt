package com.foke.together.domain.interactor.web

import com.foke.together.domain.interactor.entity.AccountData
import com.foke.together.domain.output.AccountRepositoryInterface
import com.foke.together.domain.output.RemoteRepositoryInterface
import javax.inject.Inject

class SignInUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryInterface,
    private val accountRepository: AccountRepositoryInterface,
    private val getCurrentUserInformationUseCase: GetCurrentUserInformationUseCase
) {
    suspend operator fun invoke(id: String, password: String): Result<Unit> {
        return remoteRepository.signIn(AccountData(id, password))
            .onSuccess {
                getCurrentUserInformationUseCase()
                    .onSuccess { accountData ->
                        accountRepository.setAccountInfo(AccountData(
                            email = accountData.email,
                            password = "",
                            name = accountData.name
                        ))
                    }
            }
    }
}