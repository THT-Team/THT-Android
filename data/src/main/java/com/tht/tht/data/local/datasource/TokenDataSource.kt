package com.tht.tht.data.local.datasource

interface TokenDataSource {

    suspend fun fetchFcmToken(): String?

    suspend fun updateFcmToken(token: String)

    suspend fun updateThtToken(token: String, accessTokenExpiresIn: Int)

    suspend fun fetchThtToken(): String?

}
