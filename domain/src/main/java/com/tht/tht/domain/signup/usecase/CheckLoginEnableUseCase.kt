package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.signup.repository.SignupRepository
import com.tht.tht.domain.token.repository.TokenRepository

/**
 * 회원가입 여부를 판별 후 로그인 API 호출
 * 로그인 API 결과인 Token 값을 Local 에 저장
 */
class CheckLoginEnableUseCase(
    private val signupRepository: SignupRepository,
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(phone: String): Result<Boolean> {
        return kotlin.runCatching {
            signupRepository.checkSignupState(phone)
                .also {
                    if (it.isSignup) {
                        loginRepository.refreshFcmTokenLogin(
                            fcmToken = tokenRepository.fetchFcmToken()!!,
                            phone = phone
                        ).let { tokenInfo ->
                            tokenRepository.updateThtToken(
                                tokenInfo.accessToken,
                                tokenInfo.accessTokenExpiresIn,
                                phone
                            )
                        }
                    }
                }.isSignup
        }
    }
}
