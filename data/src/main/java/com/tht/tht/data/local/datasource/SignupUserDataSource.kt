package com.tht.tht.data.local.datasource

import com.tht.tht.data.local.entity.SignupUserEntity

interface SignupUserDataSource {

    suspend fun saveSignupUser(phone: String, user: SignupUserEntity): SignupUserEntity

    suspend fun fetchSignupUser(phone: String): SignupUserEntity?

    suspend fun removeSignupUser(phone: String): Boolean
}
