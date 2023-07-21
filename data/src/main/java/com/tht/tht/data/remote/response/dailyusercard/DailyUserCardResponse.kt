package com.tht.tht.data.remote.response.dailyusercard


import com.google.gson.annotations.SerializedName

data class DailyUserCardResponse(
    @SerializedName("selectDailyFallingIdx")
    val selectDailyFallingIdx: Int,
    @SerializedName("topicExpirationUnixTime")
    val topicExpirationUnixTime: Long,
    @SerializedName("userInfos")
    val userInfos: List<UserInfo>
) {
    data class UserInfo(
        @SerializedName("address")
        val address: String,
        @SerializedName("age")
        val age: Int,
        @SerializedName("idealTypeResponseList")
        val idealTypeResponseList: List<IdealTypeResponse>,
        @SerializedName("interestResponses")
        val interestResponses: List<InterestResponse>,
        @SerializedName("introduction")
        val introduction: String,
        @SerializedName("isBirthDay")
        val isBirthDay: Boolean,
        @SerializedName("userDailyFallingCourserIdx")
        val userDailyFallingCourserIdx: Int,
        @SerializedName("userProfilePhotos")
        val userProfilePhotos: List<UserProfilePhoto>,
        @SerializedName("userUuid")
        val userUuid: String,
        @SerializedName("username")
        val username: String
    ) {
        data class IdealTypeResponse(
            @SerializedName("emojiCode")
            val emojiCode: String,
            @SerializedName("idx")
            val idx: Long,
            @SerializedName("name")
            val name: String
        )

        data class InterestResponse(
            @SerializedName("emojiCode")
            val emojiCode: String,
            @SerializedName("idx")
            val idx: Long,
            @SerializedName("name")
            val name: String
        )

        data class UserProfilePhoto(
            @SerializedName("priority")
            val priority: Int,
            @SerializedName("url")
            val url: String
        )
    }
}
