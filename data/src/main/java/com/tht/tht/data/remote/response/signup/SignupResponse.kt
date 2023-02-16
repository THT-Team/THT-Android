package com.tht.tht.data.remote.response.signup

import com.google.gson.annotations.SerializedName

data class SignupResponse(
    @SerializedName("user_id")
    val userId : String
)
