package com.foke.together.domain.interactor.web

import com.foke.together.domain.interactor.entity.AccountData
import com.foke.together.domain.output.AccountRepositoryInterface
import com.foke.together.domain.output.RemoteRepositoryInterface
import javax.inject.Inject

class GetCurrentUserInformationUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryInterface,
    private val accountRepository: AccountRepositoryInterface
) {
    suspend operator fun invoke(): Result<AccountData> =
        accountRepository.getAccountInfo()
            .onFailure {
                remoteRepository.getAccountStatus()
                    .onSuccess {
                        accountRepository.setAccountInfo(it)
                    }
            }
}