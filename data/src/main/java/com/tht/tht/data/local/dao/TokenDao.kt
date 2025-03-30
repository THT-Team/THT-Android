package com.tht.tht.data.local.dao

import com.tht.tht.data.local.entity.AccessTokenEntity

interface TokenDao {

    suspend fun fetchFcmToken(): String

    fun updateFcmToken(token: String)

    fun updateThtToken(token: String, accessTokenExpiresIn: Long, phone: String, userUuid: String?)

    fun fetchThtToken(): AccessTokenEntity

    fun fetchPhone(): String?

    fun fetchUserUuid(): String?

    fun clear()
}
