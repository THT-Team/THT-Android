package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoveSignupUserUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    internal suspend operator fun invoke(phone: String): Boolean {
        return withContext(dispatcher) {
            repository.removeSignupUser(phone)
        }
    }

}
