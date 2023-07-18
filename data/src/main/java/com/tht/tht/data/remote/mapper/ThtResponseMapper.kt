package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.base.BaseResponse
import com.tht.tht.data.remote.response.base.ThtResponse

inline fun <reified T : Any, R : Any> ThtResponse<out T>.toUnwrap(action: (T) -> R): R {
    return when (this) {
        is BaseResponse.Success -> action(response)
        is BaseResponse.SuccessNoBody -> action(Unit as T)
        is BaseResponse.ApiError -> throw Exception(errorResponse.message)
        is BaseResponse.NetworkError -> throw Exception(exception.message)
        is BaseResponse.UnknownError -> throw Exception(throwable.message)
    }
}

inline fun <reified T : Any> ThtResponse<out T>.toUnwrap(): T {
    return when (this) {
        is BaseResponse.Success -> response
        is BaseResponse.SuccessNoBody -> Unit as T
        is BaseResponse.ApiError -> throw Exception(errorResponse.message)
        is BaseResponse.NetworkError -> throw Exception(exception.message)
        is BaseResponse.UnknownError -> throw Exception(throwable.message)
    }
}


