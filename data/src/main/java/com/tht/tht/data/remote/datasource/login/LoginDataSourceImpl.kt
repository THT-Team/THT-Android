package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.mapper.login.toAuthTokenModel
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.domain.login.model.AuthTokenModel

class LoginDataSourceImpl(private val apiService: THTLoginApi) : LoginDataSource {
    override suspend fun requestLogin(loginRequest: LoginRequest): AuthTokenModel {
        return apiService.requestLogin(loginRequest).toUnwrap {
            it.toAuthTokenModel()
        }
    }
}
