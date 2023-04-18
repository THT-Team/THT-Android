package com.tht.tht.data.remote.response.nickname


import com.google.gson.annotations.SerializedName

data class NicknameDuplicateCheckResponse(
    @SerializedName("isDuplicate")
    val isDuplicate: Boolean
)
