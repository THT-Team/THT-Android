package com.tht.tht.data.remote.request.user

import com.google.gson.annotations.SerializedName

data class UserReportRequest(
    @SerializedName("reportUserUuid")
    val reportUserUuid: String,
    @SerializedName("reason")
    val reason: String
)
