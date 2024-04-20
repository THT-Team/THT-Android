package com.tht.tht.domain.setting.model

data class MyPageUserInfoModel(
    val userUuid: String,
    val username: String,
    val userProfilePhotos: List<UserProfilePhoto>,
    val birth: String,
    val gender: String,
    val introduction: String,
    val preferredGender: String,
    val height: Int,
    val smoke: String,
    val drink: String,
    val religion: String,
    val idealTypeList: List<IdealType>,
    val interestsList: List<Interests>
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
