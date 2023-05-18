package com.tht.tht.domain.token.repository

interface TokenRepository {

    suspend fun fetchFcmToken(): String?

    suspend fun updateFcmToken(token: String)

    suspend fun updateThtToken(token: String, accessTokenExpiresIn: Int, phone: String)

    suspend fun fetchThtToken(): String?

    suspend fun fetchPhone(): String?
}
