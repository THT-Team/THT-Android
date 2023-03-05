package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.login.LoginResponse

interface LoginDataSource {

    suspend fun requestLogin(loginRequest: LoginRequest): ThtResponse<LoginResponse>
}
