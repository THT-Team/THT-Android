package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PatchSignupIdealTypeUseCase(
    private val repository: SignupRepository,
    private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(phone: String, idealTypeKeys: List<Long>): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                if(idealTypeKeys.size <SignupConstant.IDEAL_TYPE_REQUIRE_SIZE){
                    throw SignupException.InputDataRequireSizeException("ideal", SignupConstant.IDEAL_TYPE_REQUIRE_SIZE)
                }
                repository.patchSignupUser(
                    phone,
                    requireNotNull(repository.fetchSignupUser(phone)){
                        throw SignupException.SignupUserInvalidateException()
                    }.copy(idealTypeKeys = idealTypeKeys))

            }
        }
    }
}
