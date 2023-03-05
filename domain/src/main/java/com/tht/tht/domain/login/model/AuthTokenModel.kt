package com.tht.tht.domain.login.model

data class AuthTokenModel(
    val accessToken: String,
    val refreshToken: String,
)
