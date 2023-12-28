package com.tht.tht.domain.login.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.repository.TokenRepository

/**
 * 로그인
 */
class LoginUseCase(
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(
        phone: String
    ): Result<Unit> {
        return kotlin.runCatching {
            loginRepository.refreshFcmTokenLogin(
                fcmToken = tokenRepository.fetchFcmToken(),
                phone = phone
            ).let { tokenInfo ->
                tokenRepository.updateThtToken(
                    tokenInfo.accessToken,
                    tokenInfo.accessTokenExpiresIn,
                    phone
                )
            }
        }
    }
}
