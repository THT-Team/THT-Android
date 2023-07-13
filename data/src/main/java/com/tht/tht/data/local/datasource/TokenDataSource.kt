package com.tht.tht.data.local.datasource

interface TokenDataSource {

    suspend fun fetchFcmToken(): String

    suspend fun updateFcmToken(token: String)

    suspend fun updateThtToken(token: String, accessTokenExpiresIn: Long, phone: String)

    suspend fun fetchThtToken(): String?

    suspend fun fetchPhone(): String?

}
