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

    override suspend fun updateThtToken(token: String, accessTokenExpiresIn: Int) {
        tokenDataSource.updateThtToken(token, accessTokenExpiresIn)
    }

    override suspend fun fetchThtToken(): String? {
        return tokenDataSource.fetchThtToken()
    }
}
