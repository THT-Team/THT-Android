package com.tht.tht.data.local.datasource

import com.tht.tht.data.local.entity.AccessTokenEntity

interface TokenDataSource {

    suspend fun fetchFcmToken(): String

    suspend fun updateFcmToken(token: String)

    suspend fun updateThtToken(token: String, accessTokenExpiresIn: Long, phone: String)

    suspend fun fetchThtToken(): AccessTokenEntity

    suspend fun fetchPhone(): String?

    suspend fun clear()
}
