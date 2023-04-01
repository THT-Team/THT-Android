package com.tht.tht.data.remote.response.login

import com.google.gson.annotations.SerializedName
import com.tht.tht.data.remote.response.common.TypeResponse

data class LoginResponse(
    @SerializedName("accessToken") val accessToken: String,
    @SerializedName("refreshToken") val refreshToken: String
)
