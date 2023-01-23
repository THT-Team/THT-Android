package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupGenderUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, gender: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(gender.isBlank()) {
                    throw SignupException.InputDataInvalidateException("gender")
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)) {
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(gender = gender)
                )
            }
        }
    }
}
