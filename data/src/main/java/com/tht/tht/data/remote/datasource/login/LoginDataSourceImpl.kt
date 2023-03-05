package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.di.IODispatcher
import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.login.LoginResponse
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.domain.login.model.AuthTokenModel
import kotlinx.coroutines.CoroutineDispatcher

class LoginDataSourceImpl(
    private val apiService: THTLoginApi,
    @IODispatcher private val dispatcher: CoroutineDispatcher
) : LoginDataSource {
    override suspend fun requestLogin(loginRequest: LoginRequest): ThtResponse<LoginResponse> {
        return apiService.requestLogin(loginRequest)
    }
}
