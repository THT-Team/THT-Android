package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupDataUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(
        phone: String,
        reduce: (SignupUserModel) -> SignupUserModel
    ): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                repository.patchSignupUser(
                    phone,
                    reduce(
                        requireNotNull(repository.fetchSignupUser(phone)){
                            throw SignupException.SignupUserInvalidateException()
                        }
                    )
                )
            }
        }
    }
}
