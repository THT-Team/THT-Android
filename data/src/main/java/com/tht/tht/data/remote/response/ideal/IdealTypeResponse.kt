package com.tht.tht.data.remote.response.ideal

import com.google.gson.annotations.SerializedName
import com.tht.tht.data.remote.response.common.TypeResponse

data class IdealTypeResponse(
    @SerializedName("name") override val name: String,
    @SerializedName("emojiCode") override val emojiCode: String,
    @SerializedName("idx") override val idx: Long
) : TypeResponse
