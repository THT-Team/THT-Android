package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupIntroduceUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, introduce: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(introduce.isBlank()) {
                    throw SignupException.InputDataInvalidateException("introduce")
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(introduce = introduce)
                )
            }
        }
    }
}
