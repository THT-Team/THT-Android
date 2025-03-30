package com.tht.tht.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class ChatHistoryResponse(
    @SerializedName("chatIdx") val chatIdx: String,
    @SerializedName("sender") val sender: String,
    @SerializedName("senderUuid") val senderUuid: String,
    @SerializedName("msg") val msg: String,
    @SerializedName("imgUrl") val imgUrl: String,
    @SerializedName("dateTime") val dateTime: String,
)
