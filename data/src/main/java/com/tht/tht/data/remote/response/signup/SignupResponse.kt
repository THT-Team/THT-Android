package com.tht.tht.data.remote.response.signup

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("accessToken")
    val accessToken : String,
    @SerializedName("accessTokenExpiresIn")
    val accessTokenExpiresIn : Long
)
