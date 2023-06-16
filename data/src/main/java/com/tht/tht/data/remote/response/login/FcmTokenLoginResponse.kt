package com.tht.tht.data.remote.response.login


import com.google.gson.annotations.SerializedName

data class FcmTokenLoginResponse(
    @SerializedName("accessToken")
    val accessToken: String,
    @SerializedName("accessTokenExpiresIn")
    val accessTokenExpiresIn: Long
)
