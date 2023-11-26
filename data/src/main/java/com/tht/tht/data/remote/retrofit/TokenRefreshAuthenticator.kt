package com.tht.tht.data.remote.retrofit

import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase
import com.tht.tht.domain.token.model.TokenException
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class TokenRefreshAuthenticator @Inject constructor(
    private val refreshFcmTokenLoginUseCase: Lazy<RefreshFcmTokenLoginUseCase>
) : Authenticator {

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

    /**
     * null 을 리턴 하면 본래 request 를 마저 수행
     */
    override fun authenticate(route: Route?, response: Response): Request? {
        return when {
            response.retryCount > (RETRY_COUNT - 1) -> null
            response.code == 401 -> response.createRequest()
            else -> null
        } ?: throw TokenException.AccessTokenRefreshFailException()
    }

    private fun Response.createRequest(): Request? {
        return try {
            runBlocking { reissueToken()?.let { request.retry(it) } } ?: request
        } catch (e: Throwable) {
            e.printStackTrace()
            null
        }
    }

    private suspend fun reissueToken(): String? =
        refreshFcmTokenLoginUseCase.get().invoke()
            .getOrNull()?.accessToken

    private fun Request.retry(accessToken: String) = this
        .newBuilder()
        .removeHeader("Authorization")
        .addHeader("Authorization", "Bearer $accessToken")
        .build()

    companion object {
        private const val RETRY_COUNT = 2
    }
}
