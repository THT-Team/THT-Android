package com.tht.tht.data.remote.retrofit

import com.tht.tht.domain.login.usecase.RefreshFcmTokenLoginUseCase
import com.tht.tht.domain.token.token.FetchThtTokenUseCase
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route


class TokenRefreshAuthenticator(
    private val refreshFcmTokenLoginUseCase: Lazy<RefreshFcmTokenLoginUseCase>,
    private val fetchThtTokenUseCase: FetchThtTokenUseCase,
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
                fetchThtTokenUseCase().getOrNull()
                    ?.let { token ->
                        val accessToken = reissueToken(token)
                        accessToken?.let { request.retry(it) }
                    }
            }
        } catch (e: Throwable) {
            null
        }
    }

    private suspend fun reissueToken(token: String): String? =
        refreshFcmTokenLoginUseCase
            .get().invoke(token)
            .getOrNull()?.accessToken

    private fun Request.retry(accessToken: String) = this
        .newBuilder()
        .removeHeader("Authorization")
        .addHeader("Authorization", "Bearer $accessToken")
        .build()
}
