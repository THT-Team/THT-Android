package com.tht.tht.data.local.mapper

import com.tht.tht.data.local.entity.SignupUserEntity
import com.tht.tht.data.local.entity.TermsEntity
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.TermsModel

// 확장 함수
fun TermsEntity.Body.toModel(): TermsModel {
    return TermsModel(
        title = title,
        content = content.map {
            TermsModel.TermsContent(
                title = it.title,
                content = it.content
            )
        },
        require = require
    )
}

fun SignupUserEntity.toModel(): SignupUserModel {
    return SignupUserModel(
        phone = phone,
        termsAgreement = termsAgreement,
        nickname = nickname,
        email = email,
        gender = gender,
        birth = birth,
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

fun SignupUserModel.toEntity(): SignupUserEntity {
    return SignupUserEntity(
        phone = phone,
        termsAgreement = termsAgreement,
        nickname = nickname,
        email = email,
        gender = gender,
        birth = birth,
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
