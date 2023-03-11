package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.domain.login.model.AuthTokenModel

interface LoginDataSource {

    suspend fun requestLogin(loginRequest: LoginRequest): AuthTokenModel
}
