package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class FetchSignupUserUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String): Result<SignupUserModel>{
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.fetchSignupUser(phone) ?: throw SignupException.SignupUserInvalidateException()
            }
        }
    }

}
