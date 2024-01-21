package com.tht.tht.domain.user

import com.tht.tht.domain.login.repository.LoginRepository

/**
 * 회원 탈퇴
 */
class UserDisActiveUseCase(
    private val logoutUseCase: LogoutUseCase,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        reason: String,
        feedback: String
    ): Result<Boolean> {
        return kotlin.runCatching {
            loginRepository.userDisActive(
                reason = reason,
                feedback = feedback
            )
            logoutUseCase().getOrThrow()
            true
        }
    }
}
