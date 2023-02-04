package com.tht.tht.data.remote.response.ideal

import com.google.gson.annotations.SerializedName

data class IdealTypeResponse(
    @SerializedName("name") val name: String,
    @SerializedName("emojiCode") val emojiCode: String,
    @SerializedName("idx") val idx: Long
)
