package com.tht.tht.domain.token.token

import com.tht.tht.domain.token.model.AccessTokenModel
import com.tht.tht.domain.token.model.TokenException
import com.tht.tht.domain.token.repository.TokenRepository

class RefreshThtAccessTokenUseCase(
    private val tokenRepository: TokenRepository
) {
    suspend operator fun invoke(): Result<AccessTokenModel> {
        return kotlin.runCatching {
            val phone = tokenRepository.fetchPhone()
            requireNotNull(phone) { "before login" }
            tokenRepository.refreshAccessToken().also { tokenInfo ->
                requireNotNull(tokenInfo.accessToken) { throw TokenException.AccessTokenRefreshFailException() }
                tokenRepository.updateThtToken(
                    tokenInfo.accessToken,
                    tokenInfo.expiredTime,
                    phone
                )
            }
        }
    }
}
