package com.foke.together.domain.output

import com.foke.together.domain.interactor.entity.AccountData
import java.io.File

interface RemoteRepositoryInterface {
    suspend fun registerAccount(data: AccountData): Result<Unit>
    suspend fun signIn(data: AccountData): Result<Unit>
    suspend fun getAccountStatus(): Result<AccountData>
    suspend fun getUploadUrl(filename: String, file: File): Result<String>
    suspend fun uploadFile(preSignedUrl: String, file: File): Result<Unit>
}