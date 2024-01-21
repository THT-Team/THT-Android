package com.tht.tht.data.remote.service

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.UserDisActiveRequest
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST

interface THTLoginApi {
    @POST(THTApiConstant.Login.FCM_TOKEN_LOGIN)
    suspend fun refreshFcmTokenLogin(
        @Body fcmTokenLoginRequest: FcmTokenLoginRequest
    ): ThtResponse<FcmTokenLoginResponse>

    @DELETE
    suspend fun userDisActive(
        @Body userDisActiveRequest: UserDisActiveRequest
    ): ThtResponse<Unit>
}
