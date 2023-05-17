package com.tht.tht.domain.login.repository

interface LoginRepository {

    suspend fun requestFcmTokenLogin(phone: String)
}
