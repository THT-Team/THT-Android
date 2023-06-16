package com.tht.tht.data.repository

import com.tht.tht.data.local.datasource.TokenDataSource
import com.tht.tht.domain.token.repository.TokenRepository
import javax.inject.Inject

class TokenRepositoryImpl @Inject constructor(
    private val tokenDataSource: TokenDataSource
) : TokenRepository {
    override suspend fun fetchFcmToken(): String? {
        return tokenDataSource.fetchFcmToken()
    }

    override suspend fun updateFcmToken(token: String) {
        tokenDataSource.updateFcmToken(token)
    }

    override suspend fun updateThtToken(token: String, accessTokenExpiresIn: Long, phone: String) {
        tokenDataSource.updateThtToken(token, accessTokenExpiresIn, phone)
    }

    override suspend fun fetchThtToken(): String? {
        return tokenDataSource.fetchThtToken()
    }

    override suspend fun fetchPhone(): String? {
        return tokenDataSource.fetchPhone()
    }
}
