package com.tht.tht.domain.token.usecase

import com.tht.tht.domain.login.repository.LoginRepository
import com.tht.tht.domain.token.repository.TokenRepository

class RequestFcmTokenLoginUseCase(
    private val tokenRepository: TokenRepository,
    private val loginRepository: LoginRepository
) {
    suspend operator fun invoke(token: String): Result<Unit> {
        return kotlin.runCatching {
            tokenRepository.updateFcmToken(token)
            loginRepository.requestFcmTokenLogin("phone") // 임시 코드
        }
    }
}
