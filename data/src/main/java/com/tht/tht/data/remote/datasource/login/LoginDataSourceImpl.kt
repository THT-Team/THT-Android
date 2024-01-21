package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.UserDisActiveRequest
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.service.THTLoginApi
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val apiService: THTLoginApi
) : LoginDataSource {
    override suspend fun refreshFcmTokenLogin(
        fcmTokenLoginRequest: FcmTokenLoginRequest
    ): FcmTokenLoginResponse {
        return apiService.refreshFcmTokenLogin(fcmTokenLoginRequest).toUnwrap()
    }

    override suspend fun userDisActive(reason: String, feedback: String) {
        apiService.userDisActive(
            UserDisActiveRequest(
                reason = reason,
                feedback = feedback
            )
        ).toUnwrap()
    }
}
