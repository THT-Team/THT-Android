package com.tht.tht.domain.token.usecase

import com.tht.tht.domain.token.repository.TokenRepository

class UpdateFcmTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(token: String): Result<Unit> {
        return kotlin.runCatching {
            tokenRepository.updateFcmToken(token)
        }
    }
}
