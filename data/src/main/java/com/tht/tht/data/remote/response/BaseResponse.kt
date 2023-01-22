package com.tht.tht.data.remote.response

import java.io.IOException

typealias ThtResponse<T> = BaseResponse<SuccessResponse<T>, ErrorResponse>

sealed class BaseResponse<out T : Any, out E : Any> {

    data class Success<T : Any>(
        val statusCode: Int,
        val response: T
    ) : BaseResponse<T, Nothing>()

    data class SuccessNoBody(val statusCode: Int) : BaseResponse<Nothing, Nothing>()

    data class ApiError<E : Any>(
        val statusCode: Int,
        val errorResponse: E,
    ) : BaseResponse<Nothing, E>()

    data class NetworkError(val exception: IOException) : BaseResponse<Nothing, Nothing>()

    data class UnknownError(val throwable: Throwable) : BaseResponse<Nothing, Nothing>()
}
