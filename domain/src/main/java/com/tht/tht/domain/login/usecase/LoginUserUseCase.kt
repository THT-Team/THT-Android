package com.tht.tht.domain.login.usecase

import com.tht.tht.domain.login.model.AuthTokenModel
import com.tht.tht.domain.login.repository.AuthRepository
import com.tht.tht.domain.type.SignInType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class LoginUserUseCase(
    private val repository: AuthRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(signInType: SignInType): Result<AuthTokenModel> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.loginUser(signInType)
            }
        }
    }
}
