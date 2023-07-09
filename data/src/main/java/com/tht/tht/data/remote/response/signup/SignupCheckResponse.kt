package com.tht.tht.data.remote.response.signup

import com.google.gson.annotations.SerializedName

data class SignupCheckResponse(
    @SerializedName("isSignUp")
    val isSignup: Boolean,
    @SerializedName("typeList")
    val loginTypeList: List<String>
)
