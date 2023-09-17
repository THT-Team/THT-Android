package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse

interface LoginDataSource {
    suspend fun refreshFcmTokenLogin(fcmTokenLoginRequest: FcmTokenLoginRequest): FcmTokenLoginResponse
}
