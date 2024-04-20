package com.tht.tht.data.remote.response.setting


import com.google.gson.annotations.SerializedName

data class MyPageUserInfoResponse(
    @SerializedName("address")
    val address: String,
    @SerializedName("age")
    val age: Int,
    @SerializedName("drinking")
    val drinking: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("idealTypeList")
    val idealTypeList: List<IdealType>,
    @SerializedName("interestsList")
    val interestsList: List<Interests>,
    @SerializedName("introduction")
    val introduction: String,
    @SerializedName("phoneNumber")
    val phoneNumber: String,
    @SerializedName("prefer_gender")
    val preferGender: String,
    @SerializedName("religion")
    val religion: String,
    @SerializedName("smoking")
    val smoking: String,
    @SerializedName("tall")
    val tall: Int,
    @SerializedName("userProfilePhotos")
    val userProfilePhotos: List<UserProfilePhoto>,
    @SerializedName("userUuid")
    val userUuid: String,
    @SerializedName("username")
    val username: String
) {
    data class IdealType(
        @SerializedName("emojiCode")
        val emojiCode: String,
        @SerializedName("idx")
        val idx: Int,
        @SerializedName("name")
        val name: String
    )

    data class Interests(
        @SerializedName("emojiCode")
        val emojiCode: String,
        @SerializedName("idx")
        val idx: Int,
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
