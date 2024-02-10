package com.tht.tht.domain.token.token

import com.tht.tht.domain.token.repository.TokenRepository

/**
 * Local 에 저장된 Access Token 유효성 리턴
 */
class CheckThtAccessTokenExpiredUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(
        nowTimeMill: Long = System.currentTimeMillis()
    ): Result<Boolean> {
        return kotlin.runCatching {
            val accessToken = repository.fetchThtToken()
            !accessToken.accessToken.isNullOrBlank() &&
                accessToken.expiredTime != 0L &&
                accessToken.expiredTime >= nowTimeMill
        }
    }
}
