package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.AccountData

interface AccountRepositoryInterface {
    suspend fun setAccountInfo(data: AccountData)
    suspend fun getAccountInfo(): Result<AccountData>
}