package com.tht.tht.data.remote.response.user

import com.google.gson.annotations.SerializedName

data class UserHeartResponse(
    @SerializedName("isMatching")
    val isMatching: Boolean,
    @SerializedName("chatRoomIdx")
    val chatRoomIdx: Int?
)
