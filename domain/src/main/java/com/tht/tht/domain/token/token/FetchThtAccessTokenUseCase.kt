package com.tht.tht.domain.token.token

import com.tht.tht.domain.token.model.TokenException
import com.tht.tht.domain.token.repository.TokenRepository

class FetchThtAccessTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(): Result<String> {
        return kotlin.runCatching {
            repository.fetchThtToken().accessToken
                ?: throw TokenException.AccessTokenExpiredException()
        }
    }
}
