package com.foke.together.data.repository

import com.foke.together.domain.interactor.entity.AccountData
import com.foke.together.domain.output.AccountRepositoryInterface
import javax.inject.Inject

class AccountRepository @Inject constructor(
) : AccountRepositoryInterface {
    private var accountData: AccountData? = null

    override suspend fun setAccountInfo(data: AccountData) {
        accountData = data
    }

    override suspend fun getAccountInfo(): Result<AccountData> {
        return accountData?.run {
            Result.success(this)
        } ?: run {
            Result.failure(Exception("account data is null"))
        }
    }
}