package com.tht.tht.data.remote.request.login

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("socialType") val socialType: String,
)
