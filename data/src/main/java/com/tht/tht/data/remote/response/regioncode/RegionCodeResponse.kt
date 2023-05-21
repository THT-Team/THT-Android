package com.tht.tht.data.remote.response.regioncode

import com.google.gson.annotations.SerializedName

data class RegionCodeResponse(
    @SerializedName("StanReginCd")
    val stanReginCd: List<StanReginCd>
)
