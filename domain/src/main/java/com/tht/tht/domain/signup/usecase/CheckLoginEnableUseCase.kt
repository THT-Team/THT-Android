package com.tht.tht.domain.signup.usecase

import com.tht.tht.domain.login.usecase.LoginUseCase
import com.tht.tht.domain.signup.repository.SignupRepository

/**
 * 회원가입 여부를 판별 후 로그인 API 호출
 * 로그인 API 결과인 Token 값을 Local 에 저장
 */
class CheckLoginEnableUseCase(
    private val signupRepository: SignupRepository,
    private val loginUseCase: LoginUseCase
) {
    suspend operator fun invoke(phone: String): Result<Boolean> {
        return kotlin.runCatching {
            signupRepository.checkSignupState(phone)
                .also {
                    if (it.isSignup) {
                        loginUseCase(phone).getOrThrow()
                    }
                }.isSignup
        }
    }
}
