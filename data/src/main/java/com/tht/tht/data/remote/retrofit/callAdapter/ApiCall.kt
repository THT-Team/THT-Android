package com.tht.tht.data.remote.retrofit.callAdapter

import com.tht.tht.data.remote.response.base.BaseResponse
import okhttp3.Request
import okhttp3.ResponseBody
import okio.IOException
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Converter
import retrofit2.Response

internal class ApiCall<T : Any, E : Any>(
    private val call: Call<T>,
    private val errorConverter: Converter<ResponseBody, E>
) : Call<BaseResponse<T, E>> {

    override fun enqueue(
        callback: Callback<BaseResponse<T, E>>
    ) = call.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    val successResponse = BaseResponse.Success(
                        statusCode = response.code(),
                        response = it
                    )
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(successResponse)
                    )
                } ?: run {
                    val successNoBodyResponse = BaseResponse.SuccessNoBody(
                        statusCode = response.code()
                    )
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(successNoBodyResponse)
                    )
                }
            } else {
                val errorBody = response.errorBody()
                when {
                    errorBody == null -> null
                    errorBody.contentLength() == 0L -> null
                    else -> try {
                        errorConverter.convert(errorBody)
                    } catch (e: Exception) {
                        null
                    }
                }?.let {
                    val apiErrorResponse = BaseResponse.ApiError(
                        statusCode = response.code(),
                        errorResponse = it
                    )
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(apiErrorResponse)
                    )
                } ?: run {
                    val unknownErrorResponse = UnknownError(
                        "HTTP ${response.code()} : no response error body"
                    )
                    callback.onResponse(
                        this@ApiCall,
                        Response.success(
                            BaseResponse.UnknownError(unknownErrorResponse)
                        )
                    )
                }
            }
        }

        override fun onFailure(call: Call<T>, throwable: Throwable) {
            val networkResponse = when (throwable) {
                is IOException -> BaseResponse.NetworkError(throwable)
                else -> BaseResponse.UnknownError(throwable)
            }
            callback.onResponse(this@ApiCall, Response.success(networkResponse))
        }
    })

    override fun execute() = throw UnsupportedOperationException("Unsupported Operation")

    override fun clone() = ApiCall(call.clone(), errorConverter)

    override fun isExecuted() = call.isExecuted

    override fun isCanceled() = call.isCanceled

    override fun cancel() = call.cancel()

    override fun request(): Request = call.request()

    override fun timeout(): Timeout = call.timeout()
}
