package com.tht.tht.domain.signup.model

data class SignupUserModel(
    val phone: String,
    val termsAgreement: Map<TermsModel, Boolean>,
    val nickname: String,
    val email: String,
    val gender: String,
    val birthday: String,
    val interestKeys: List<String>,
    val lat: Double,
    val lng: Double,
    val address: String,
    val preferredGender: String,
    val profileImgUrl: List<String>,
    val introduce: String,
    val idealTypeKeys: List<Long>
) {
    companion object {
        fun getFromDefaultArgument(
            phone: String = "",
            termsAgreement: Map<TermsModel, Boolean> = emptyMap(),
            nickname: String = "",
            email: String = "",
            gender: String = "",
            birthday : String = "",
            interestKeys: List<String> = emptyList(),
            lat: Double = -1.0,
            lng: Double = -1.0,
            address: String = "",
            preferredGender: String = "",
            profileImgUrl: List<String> = emptyList(),
            introduce: String = "",
            idealTypeKeys: List<Long> = emptyList()
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
            preferredGender = preferredGender,
            profileImgUrl = profileImgUrl,
            introduce = introduce,
            idealTypeKeys = idealTypeKeys
        )
    }
}
