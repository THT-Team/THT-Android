package com.tht.tht.data.local.dao

interface TokenDao {

    suspend fun fetchFcmToken(): String?

    fun updateFcmToken(token: String)

    fun updateThtToken(token: String, accessTokenExpiresIn: Int)

    fun fetchThtToken(): String?
}
