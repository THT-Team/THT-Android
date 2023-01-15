package com.tht.tht.data.remote.response

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
    val timestamp: String,
    val path: String,
)
