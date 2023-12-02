package com.tht.tht.domain.token.repository

import com.tht.tht.domain.token.model.AccessTokenModel

interface TokenRepository {

    suspend fun fetchFcmToken(): String

    suspend fun updateFcmToken(token: String)

    suspend fun updateThtToken(token: String, accessTokenExpiresIn: Long, phone: String)

    suspend fun fetchThtToken(): AccessTokenModel

    suspend fun fetchPhone(): String?

    suspend fun refreshAccessToken(): AccessTokenModel
}
