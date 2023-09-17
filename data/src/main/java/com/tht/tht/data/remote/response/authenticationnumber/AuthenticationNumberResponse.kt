package com.tht.tht.data.remote.response.authenticationnumber

import com.google.gson.annotations.SerializedName

data class AuthenticationNumberResponse(
    @SerializedName("authNumber")
    val authNumber: Int,
    @SerializedName("phoneNumber")
    val phoneNumber: String
)
