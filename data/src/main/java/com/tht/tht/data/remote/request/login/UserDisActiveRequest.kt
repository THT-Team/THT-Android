package com.tht.tht.data.remote.request.login

import com.google.gson.annotations.SerializedName

data class UserDisActiveRequest(
    @SerializedName("reason")
    val reason: String,
    @SerializedName("feedBack")
    val feedback: String
)
