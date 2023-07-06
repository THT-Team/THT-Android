package com.tht.tht.domain.login.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.repository.TokenRepository

/**
 * 이미 로그인 한 유저가 Fcm Token 이 갱신 되었을 때 호출
 */
class RefreshFcmTokenLoginUseCase(
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(fcmToken: String): Result<Unit> {
        return kotlin.runCatching {
            tokenRepository.updateFcmToken(fcmToken) // local 에 fcm token 저장
            val phone = tokenRepository.fetchPhone()
            requireNotNull(phone) { "before login" }
            loginRepository.requestFcmTokenLogin(fcmToken, phone).let {
                tokenRepository.updateThtToken(
                    it.accessToken,
                    it.accessTokenExpiresIn,
                    phone
                )
            }
        }
    }
}
