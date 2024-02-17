package com.tht.tht.domain.setting.model

data class MyPageUserInfoModel(
    val address: String,
    val age: Int,
    val email: String,
    val idealTypeList: List<IdealType>,
    val interestsList: List<Interests>,
    val introduction: String,
    val phoneNumber: String,
    val userProfilePhotos: List<UserProfilePhoto>,
    val userUuid: String,
    val username: String
) {
    data class IdealType(
        val emojiCode: String,
        val idx: Int,
        val name: String
    )

    data class Interests(
        val emojiCode: String,
        val idx: Int,
        val name: String
    )

    data class UserProfilePhoto(
        val priority: Int,
        val url: String
    )
}
