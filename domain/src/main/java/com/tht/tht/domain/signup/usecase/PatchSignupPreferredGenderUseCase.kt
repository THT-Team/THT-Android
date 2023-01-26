package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupPreferredGenderUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, preferredGender: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(preferredGender.isBlank()) {
                    throw SignupException.InputDataInvalidateException("preferredGender")
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(preferredGender = preferredGender)
                )
            }
        }
    }
}

