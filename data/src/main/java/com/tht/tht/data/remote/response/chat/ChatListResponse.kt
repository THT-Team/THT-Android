package com.tht.tht.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class ChatListResponse(
    @SerializedName("chatRoomIdx") val chatRoomIdx: Long,
    @SerializedName("partnerName") val partnerName: String,
    @SerializedName("partnerProfileUrl") val partnerProfileUrl: String,
    @SerializedName("currentMessage") val currentMessage: String,
    @SerializedName("messageTime") val messageTime: String,
)