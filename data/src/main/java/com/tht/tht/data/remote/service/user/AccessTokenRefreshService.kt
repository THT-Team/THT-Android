package com.tht.tht.data.remote.service.user

import com.tht.tht.data.constant.THTApiConstant
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.user.AccessTokenRefreshResponse
import retrofit2.http.POST

interface AccessTokenRefreshService {
    @POST(THTApiConstant.User.ACCESS_TOKEN_REFRESH)
    suspend fun refreshAccessToken(): ThtResponse<AccessTokenRefreshResponse>
}
