package com.tht.tht.domain.token.token

import com.tht.tht.domain.token.repository.TokenRepository

class FetchThtTokenUseCase(
    private val repository: TokenRepository
) {
    suspend operator fun invoke(): Result<String> {
        return kotlin.runCatching {
            repository.fetchThtToken()!!
        }
    }
}
