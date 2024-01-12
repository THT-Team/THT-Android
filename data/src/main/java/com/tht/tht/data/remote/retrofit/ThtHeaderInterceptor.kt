package com.tht.tht.data.remote.retrofit

import android.util.Log
import com.google.gson.Gson
import com.tht.tht.data.remote.response.base.ErrorResponse
import com.tht.tht.data.remote.retrofit.header.HttpHeaderKey
import com.tht.tht.domain.token.model.TokenException
import com.tht.tht.domain.token.token.FetchThtAccessTokenUseCase
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import javax.inject.Inject

class ThtHeaderInterceptor @Inject constructor(
    private val gson: Gson,
    private val fetchThtAccessTokenUseCase: Lazy<FetchThtAccessTokenUseCase>
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
            .header(HttpHeaderKey.CONTENT_TYPE_HEADER_KEY, HttpHeaderKey.CONTENT_TYPE_HEADER_VALUE)
        val accessToken = runBlocking { fetchThtAccessTokenUseCase.get().invoke().getOrNull() }
        if (accessToken != null) {
            requestBuilder.header(
                HttpHeaderKey.AUTHORIZATION_HEADER_KEY,
                "${HttpHeaderKey.BEARER_PREFIX} $accessToken"
            )
        }

        val response = chain.proceed(requestBuilder.build())
        return response.body?.string()?.let {
            checkTokenExpiredException(response, parseErrorResponse(it))
            //response 는 한 번 밖에 읽지 못함
            response.newBuilder()
                .body(ResponseBody.create(response.body!!.contentType(), it))
                .build()
        } ?: kotlin.run {
            checkTokenExpiredException(response, null)
            response
        }
    }

    private fun parseErrorResponse(errorJson: String): ErrorResponse? {
        return try {
            gson.fromJson(errorJson, ErrorResponse::class.java)
        } catch (e : Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun checkTokenExpiredException(
        response: Response,
        errorResponse: ErrorResponse?
    ) {
        when {
            errorResponse?.error == "refresh_token_expired" &&
                response.code == 500 -> {
                throw TokenException.RefreshTokenExpiredException()
            }

        }
    }
}
