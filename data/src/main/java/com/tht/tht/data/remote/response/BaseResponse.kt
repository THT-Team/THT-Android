package com.tht.tht.data.remote.response

import java.io.IOException

sealed class BaseResponse<T, E> {

    data class Success<T>(
        val statusCode: Int,
        val response: T
    ) : BaseResponse<T, Nothing>()

    data class SuccessNoBody(val statusCode: Int) : BaseResponse<Nothing, Nothing>()

    data class ApiError<E>(
        val statusCode: Int,
        val errorResponse: E,
    ) : BaseResponse<Nothing, E>()

    data class NetworkError(val exception: IOException) : BaseResponse<Nothing, Nothing>()

    data class UnknownError(val throwable: Throwable) : BaseResponse<Nothing, Nothing>()
}

typealias ThtResponse<T> = BaseResponse<SuccessResponse<T>, ErrorResponse>
