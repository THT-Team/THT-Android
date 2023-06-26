package com.tht.tht.domain.signup.model

data class SignupUserModel(
    val phone: String,
    val termsAgreement: Map<TermsModel, Boolean>,
    val nickname: String,
    val email: String,
    val gender: String,
    val birthday: String,
    val interestKeys: List<Long>,
    val lat: Double,
    val lng: Double,
    val address: String,
    val regionCode: String,
    val preferredGender: String,
    val profileImgUrl: List<String>,
    val introduce: String,
    val idealTypeKeys: List<Long>,
    val fcmToken: String,
    val snsType: String,
    val snsUniqueId: String
) {
    companion object {
        fun getFromDefaultArgument(
            phone: String = "",
            termsAgreement: Map<TermsModel, Boolean> = emptyMap(),
            nickname: String = "",
            email: String = "",
            gender: String = "",
            birthday : String = "",
            interestKeys: List<Long> = emptyList(),
            lat: Double = -1.0,
            lng: Double = -1.0,
            address: String = "",
            regionCode: String = "",
            preferredGender: String = "",
            profileImgUrl: List<String> = emptyList(),
            introduce: String = "",
            idealTypeKeys: List<Long> = emptyList(),
            fcmToken: String = "",
            snsType: String = "",
            snsUniqueId: String = ""
        ): SignupUserModel = SignupUserModel(
            phone = phone,
            termsAgreement = termsAgreement,
            nickname = nickname,
            email = email,
            gender = gender,
            birthday = birthday,
            interestKeys = interestKeys,
            lat = lat,
            lng = lng,
            address = address,
            regionCode = regionCode,
            preferredGender = preferredGender,
            profileImgUrl = profileImgUrl,
            introduce = introduce,
            idealTypeKeys = idealTypeKeys,
            fcmToken = fcmToken,
            snsType = snsType,
            snsUniqueId = snsUniqueId
        )
    }
}
