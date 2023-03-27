package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestAuthenticationUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(phone: String): Result<String> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.requestAuthentication(phone)
            }
        }
    }
}
