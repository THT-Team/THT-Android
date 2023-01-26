package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupEmailUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, email: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(email.isBlank()) {
                    throw SignupException.InputDataInvalidateException("email")
                }
                repository.patchSignupUser(
                    phone, requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(email = email)
                )
            }
        }
    }
}
