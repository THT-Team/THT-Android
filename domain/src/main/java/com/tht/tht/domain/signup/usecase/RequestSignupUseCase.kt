package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestSignupUseCase(
    private val repository: SignupRepository,
    private val removeSignupUserUseCase: RemoveSignupUserUseCase,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(phone: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                val user = requireNotNull(repository.fetchSignupUser(phone))
                when {
                    user.phone.isBlank() ||
                        user.nickname.isBlank() ||
                        user.email.isBlank() ||
                        user.gender.isBlank() ||
                        user.birthday.isBlank() ||
                        user.interestKeys.size < SignupConstant.INTEREST_REQUIRE_SIZE ||
                        user.lat < 0 ||
                        user.lng < 0 ||
                        user.address.isBlank() ||
                        user.preferredGender.isBlank() ||
                        user.profileImgUrl.size < SignupConstant.PROFILE_IMAGE_REQUIRE_SIZE ||
                        user.introduce.isBlank() ||
                        user.idealTypeKeys.size < SignupConstant.IDEAL_TYPE_REQUIRE_SIZE -> throw SignupException.SignupUserInfoInvalidateException()
                }

                repository.requestSignup(user).also {
                    if(it) {
                        removeSignupUserUseCase(phone)
                    }
                }
            }
        }
    }
}
