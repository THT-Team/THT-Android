package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupNickNameUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, nickname: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(nickname.isBlank()) {
                    throw SignupException.InputDataInvalidateException("nickname")
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(nickname = nickname)
                )
            }
        }
    }
}
