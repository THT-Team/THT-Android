package com.tht.tht.domain.login.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel
import com.tht.tht.domain.token.repository.TokenRepository

/**
 * 이미 로그인 한 유저가 Fcm Token 이 갱신 되었을 때 호출
 */
class RefreshFcmTokenLoginUseCase(
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(fcmToken: String? = null): Result<FcmTokenLoginResponseModel> {
        return kotlin.runCatching {
            val fcmTokenNonNull = fcmToken ?: tokenRepository.fetchFcmToken()
            tokenRepository.updateFcmToken(fcmTokenNonNull) // local 에 fcm token 저장
            val phone = tokenRepository.fetchPhone()
            requireNotNull(phone) { "before login" }
            loginRepository.refreshFcmTokenLogin(fcmTokenNonNull, phone).let {
                tokenRepository.updateThtToken(
                    it.accessToken,
                    it.accessTokenExpiresIn,
                    phone
                )
                it
            }
        }
    }
}
