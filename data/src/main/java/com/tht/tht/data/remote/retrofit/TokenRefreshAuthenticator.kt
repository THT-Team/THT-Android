package com.tht.tht.data.remote.retrofit

import com.tht.tht.data.local.dao.TokenDao
import com.tht.tht.data.remote.request.login.FcmTokenLoginRequest
import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.service.THTLoginApi
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject


class TokenRefreshAuthenticator @Inject constructor(
    private val tokenDao: TokenDao,
) : Authenticator {
    @Inject
    lateinit var apiClient: ApiClient
    private val Response.retryCount: Int
        get() {
            var currentResponse = priorResponse
            var result = 0
            while (currentResponse != null) {
                result++
                currentResponse = currentResponse.priorResponse
            }
            return result
        }

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.code == 401) {
            response.createRequest()
        } else if (response.retryCount > 2) {
            null
        } else {
            null
        }
    }

    private fun Response.createRequest(): Request? {
        return try {
            runBlocking {
                val result = tokenDao.fetchFcmToken()?.let { token ->
                    tokenDao.fetchPhone()?.let { phoneNumber ->
                        reissueToken(token, phoneNumber)
                    }
                }
                when (result) {
                    is BaseResponse.Success -> request.retry(result.response.accessToken)
                    else -> null
                }
            }
        } catch (e: Throwable) {
            null
        }
    }

    private suspend fun reissueToken(
        token: String,
        phoneNumber: String
    ): ThtResponse<FcmTokenLoginResponse> {
        return apiClient.thtApiAdapter
            .create(THTLoginApi::class.java).run {
                refreshFcmTokenLogin(
                    fcmTokenLoginRequest = FcmTokenLoginRequest(
                        deviceKey = token,
                        phoneNumber = phoneNumber,
                    )
                )
            }
    }

    private fun Request.retry(accessToken: String) = this
        .newBuilder()
        .removeHeader("Authorization")
        .addHeader("Authorization", "Bearer $accessToken")
        .build()
}
