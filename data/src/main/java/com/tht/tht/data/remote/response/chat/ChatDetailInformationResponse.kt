package com.tht.tht.data.remote.response.chat

import com.google.gson.annotations.SerializedName

data class ChatDetailInformationResponse(
    @SerializedName("chatRoomIdx") val chatRoomIdx: Long,
    @SerializedName("talkSubject") val talkSubject: String,
    @SerializedName("talkIssue") val talkIssue: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("isChatAble") val isChatAble: Boolean
)
