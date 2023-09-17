package com.tht.tht.data.remote.request.login

import com.google.gson.annotations.SerializedName

data class FcmTokenLoginRequest(
    @SerializedName("deviceKey")
    val deviceKey: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)
