package com.tht.tht.data.remote.response.regioncode

import com.google.gson.annotations.SerializedName

data class Row(
    @SerializedName("region_cd")
    val regionCode: String,
)
