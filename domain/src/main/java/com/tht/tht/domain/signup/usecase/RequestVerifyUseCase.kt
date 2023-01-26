package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestVerifyUseCase(
    private val repository: SignupRepository,
    private val createSignupUserUseCase: CreateSignupUserUseCase,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(phone: String, auth: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.requestVerify(phone, auth).also {
                    if(it) {
                        createSignupUserUseCase(phone)
                    }
                }
            }
        }
    }
}
