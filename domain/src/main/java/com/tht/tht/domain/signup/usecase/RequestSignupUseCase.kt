package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.signup.constant.SignupConstant
import com.tht.tht.domain.signup.model.SignupException
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.token.model.TokenException
import com.tht.tht.domain.token.repository.TokenRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RequestSignupUseCase(
    private val signupRepository: SignupRepository,
    private val tokenRepository: TokenRepository,
    private val removeSignupUserUseCase: RemoveSignupUserUseCase,
    private val dispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(phone: String): Result<Boolean> {
        return kotlin.runCatching {
            withContext(dispatcher) {
                val user = requireNotNull(signupRepository.fetchSignupUser(phone))
                val fcmToken = tokenRepository.fetchFcmToken()
                when {
                    user.phone.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("phone")

                    user.nickname.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("nickname")

                    user.email.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("email")

                    user.gender.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("gender")

                    user.birthday.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("birthday")

                    user.interestKeys.size < SignupConstant.INTEREST_REQUIRE_SIZE -> throw SignupException.SignupUserInfoInvalidateException("interest")

                    user.lat < 0 -> throw SignupException.SignupUserInfoInvalidateException("lat")

                    user.lng < 0 -> throw SignupException.SignupUserInfoInvalidateException("lng")

                    user.address.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("address")

                    user.regionCode.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("regionCode")

                    user.preferredGender.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("preferred gender")

                    user.profileImgUrl.size < SignupConstant.PROFILE_IMAGE_REQUIRE_SIZE -> throw SignupException.SignupUserInfoInvalidateException("profile image")

                    user.introduce.isBlank() -> throw SignupException.SignupUserInfoInvalidateException("introduce")

                    user.idealTypeKeys.size < SignupConstant.IDEAL_TYPE_REQUIRE_SIZE -> throw SignupException.SignupUserInfoInvalidateException("ideal")

                    fcmToken.isNullOrBlank() -> throw TokenException.NoneTokenException()
                }

                signupRepository.requestSignup(
                    user.copy(
                        fcmToken = fcmToken!!
                    )
                ).let {
                    removeSignupUserUseCase(phone)
                    tokenRepository.updateThtToken(it.accessToken, it.accessTokenExpiresIn, phone)
                    it.accessToken.isNotBlank()
                }
            }
        }
    }
}
