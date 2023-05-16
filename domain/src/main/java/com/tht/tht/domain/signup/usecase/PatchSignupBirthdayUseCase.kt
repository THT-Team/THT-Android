package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupBirthdayUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, gender: String, birthday: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(birthday.isBlank()) {
                    throw SignupException.InputDataInvalidateException("birthday")
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(gender = gender, birthday = birthday)
                )
            }
        }
    }
}
