package com.tht.tht.domain.token.model

data class AccessTokenModel(
    val accessToken: String?,
    val expiredTime: Long
)
