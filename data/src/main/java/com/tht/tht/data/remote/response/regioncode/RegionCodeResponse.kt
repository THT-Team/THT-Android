package com.tht.tht.data.remote.response.regioncode

import com.google.gson.annotations.SerializedName

data class RegionCodeResponse(
    @SerializedName("StanReginCd")
    val stanReginCd: List<StanReginCd>
) {
    data class StanReginCd(
        val row: List<Row>?
    ) {
        data class Row(
            @SerializedName("region_cd")
            val regionCode: String
        )
    }
}
