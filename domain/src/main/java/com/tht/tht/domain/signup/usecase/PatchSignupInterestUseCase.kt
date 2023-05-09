package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupInterestUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, interestKeys: List<Long>): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(interestKeys.size <SignupConstant.INTEREST_REQUIRE_SIZE){
                    throw SignupException.InputDataRequireSizeException("interest", SignupConstant.INTEREST_REQUIRE_SIZE)
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(interestKeys = interestKeys)
                )
            }
        }
    }
}
