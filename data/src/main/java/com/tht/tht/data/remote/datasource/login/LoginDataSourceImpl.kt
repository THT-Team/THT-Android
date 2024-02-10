package com.tht.tht.data.remote.datasource.login

import com.tht.tht.data.remote.mapper.toUnwrap
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.request.login.UserDisActiveRequest
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.service.THTLoginApi
import com.tht.tht.data.remote.service.user.UserDisActiveService
import javax.inject.Inject

class LoginDataSourceImpl @Inject constructor(
    private val apiService: THTLoginApi,
    private val userDisActiveService: UserDisActiveService
) : LoginDataSource {
    override suspend fun refreshFcmTokenLogin(
        fcmTokenLoginRequest: FcmTokenLoginRequest
    ): FcmTokenLoginResponse {
        return apiService.refreshFcmTokenLogin(fcmTokenLoginRequest).toUnwrap()
    }

    override suspend fun userDisActive(reason: String, feedback: String) {
        userDisActiveService.userDisActive(
            UserDisActiveRequest(
                reason = reason,
                feedback = feedback
            )
        ).toUnwrap()
    }
}
