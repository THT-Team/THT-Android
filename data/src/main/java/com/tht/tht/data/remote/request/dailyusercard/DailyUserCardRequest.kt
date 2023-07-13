package com.tht.tht.data.remote.request.dailyusercard


import com.google.gson.annotations.SerializedName

data class DailyUserCardRequest(
    @SerializedName("alreadySeenUserUuidList")
    val alreadySeenUserUuidList: List<String>,
    @SerializedName("size")
    val size: Int,
    @SerializedName("userDailyFallingCourserIdx")
    val userDailyFallingCourserIdx: Int?
)
