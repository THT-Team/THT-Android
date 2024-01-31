package com.tht.tht.data.di.websocket

import com.google.gson.annotations.SerializedName

data class AllMarketResponse(
    @SerializedName("id")
    val id: Int,

    @SerializedName("result")
    val result: Any? = null,

    @SerializedName("stream")
    val stream: String = "",

    @SerializedName("data")
    val data: List<Ticker> = emptyList()
)
