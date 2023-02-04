package com.tht.tht.data.remote.response.base

data class SuccessResponse<T>(
    val status: Int,
    val body: T,
)
