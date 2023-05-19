package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.LoginRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.response.login.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface THTLoginApi {

    @POST(THTApiConstant.Login.LOGIN)
    suspend fun requestLogin(
        @Body loginRequest: LoginRequest
    ): ThtResponse<LoginResponse>

    @POST(THTApiConstant.Login.FCM_TOKEN_LOGIN)
    suspend fun requestFcmTokenLogin(
        @Body fcmTokenLoginRequest: FcmTokenLoginRequest
    ): ThtResponse<FcmTokenLoginResponse>
}
