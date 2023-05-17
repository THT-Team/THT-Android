package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.mapper.login.toAuthTokenModel
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.domain.login.model.AuthTokenModel
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val apiService: THTLoginApi
) : LoginDataSource {
    override suspend fun requestLogin(loginRequest: LoginRequest): AuthTokenModel {
        return apiService.requestLogin(loginRequest).toUnwrap {
            it.toAuthTokenModel()
        }
    }

    override suspend fun requestFcmTokenLogin(fcmTokenLoginRequest: FcmTokenLoginRequest): FcmTokenLoginResponse {
        return apiService.requestFcmTokenLogin(fcmTokenLoginRequest).toUnwrap { it }
    }
}
