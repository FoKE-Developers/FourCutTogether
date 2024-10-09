package com.foke.together.domain.interactor.web

import com.foke.together.domain.output.RemoteRepositoryInterface
import javax.inject.Inject

class CreateAccountUseCase @Inject constructor(
    private val remoteRepository: RemoteRepositoryInterface
)  {
    operator fun invoke(id: String, password: String): Result<Unit> {
        // TODO: implement in sprint4
        return Result.failure(Exception("not implemented"))
    }
}