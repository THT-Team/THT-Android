package com.tht.tht.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class AccessTokenRefreshResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessTokenExpiresIn")
    val accessTokenExpiresIn: Long
)
