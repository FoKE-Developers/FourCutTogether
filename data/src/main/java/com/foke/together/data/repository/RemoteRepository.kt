package com.foke.together.data.repository

import com.foke.together.data.datasource.remote.WebDataSource
import com.foke.together.domain.interactor.entity.AccountData
import com.foke.together.domain.output.RemoteRepositoryInterface
import java.io.File
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val webDataSource: WebDataSource
) : RemoteRepositoryInterface {
    override suspend fun registerAccount(data: AccountData): Result<Unit> {
        return data.name?.let { name ->
            webDataSource.accountRegister(data.email, name, data.password)
                .onSuccess {
                    return Result.success(Unit)
                }
                .onFailure {
                    return Result.failure(it)
                }
            Result.failure(Exception("unknown error"))
        } ?: Result.failure(Exception("name is null"))
    }

    override suspend fun signIn(data: AccountData): Result<Unit> =
        webDataSource.accountSignIn(data.email, data.password)

    override suspend fun getAccountStatus(): Result<AccountData> {
        webDataSource.accountWhoAmI()
            .onSuccess {
                return Result.success(
                    AccountData(it.email, "", it.name)
                )
            }
            .onFailure {
                return Result.failure(it)
            }
        return Result.failure(Exception("unknown error"))
    }

    override suspend fun getUploadUrl(key: String, file: File): Result<String> {
        webDataSource.s3PreSignedUrl(key, file)
            .onSuccess {
                return Result.success(it.presignedUrl)
            }
            .onFailure {
                return Result.failure(it)
            }
        return Result.failure(Exception("unknown error"))
    }

    override suspend fun uploadFile(preSignedUrl: String, file: File): Result<Unit> {
        webDataSource.sendFileToCloud(preSignedUrl, file)
            .onSuccess {
                return Result.success(Unit)
            }
            .onFailure {
                return Result.failure(it)
            }
        return Result.failure(Exception("unknown error"))
    }

    companion object {
        val TAG = RemoteRepository::class.java.simpleName
    }
}