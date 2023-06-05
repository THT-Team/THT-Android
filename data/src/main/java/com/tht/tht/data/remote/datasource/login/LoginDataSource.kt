package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.domain.login.model.AuthTokenModel

interface LoginDataSource {

    suspend fun requestLogin(loginRequest: LoginRequest): AuthTokenModel

    suspend fun requestFcmTokenLogin(fcmTokenLoginRequest: FcmTokenLoginRequest): FcmTokenLoginResponse
}
