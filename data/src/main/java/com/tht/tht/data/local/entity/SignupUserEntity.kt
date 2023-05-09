package com.tht.tht.data.local.entity

import com.tht.tht.domain.signup.model.TermsModel

data class SignupUserEntity(
    val phone: String,
    val termsAgreement: Map<String, Pair<TermsModel, Boolean>>,
    val nickname: String,
    val email: String,
    val gender: String,
    val birthday: String,
    val interestKeys: List<Long>,
    val lat: Double,
    val lng: Double,
    val address: String,
    val preferredGender: String,
    val profileImgUrl: List<String>,
    val introduce: String,
    val idealTypeKeys: List<Long>
) : java.io.Serializable
