package com.tht.tht.data.local.mapper

import com.tht.tht.data.local.entity.SignupUserEntity
import com.tht.tht.data.local.entity.TermsEntity
import com.tht.tht.data.remote.response.location.LocationResponse
import com.tht.tht.data.remote.response.regioncode.RegionCodeResponse
import com.tht.tht.domain.signup.model.LocationModel
import com.tht.tht.domain.signup.model.RegionCodeModel
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.TermsModel

// 확장 함수
fun TermsEntity.Body.toModel(): TermsModel {
    return TermsModel(
        title = title,
        key = key,
        description = description,
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
    val map = mutableMapOf<TermsModel, Boolean>()
    termsAgreement.forEach {
        map[it.value.first] = it.value.second
    }
    return SignupUserModel(
        phone = phone,
        termsAgreement = map,
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
        idealTypeKeys = idealTypeKeys,
        fcmToken = fcmToken,
        snsType = snsType,
        snsUniqueId = snsUniqueId
    )
}

fun SignupUserModel.toEntity(): SignupUserEntity {
    val map = mutableMapOf<String, Pair<TermsModel, Boolean>>()
    termsAgreement.forEach {
        map[it.key.title] = it.key to it.value
    }
    return SignupUserEntity(
        phone = phone,
        termsAgreement = map,
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
        idealTypeKeys = idealTypeKeys,
        fcmToken = fcmToken,
        snsType = snsType,
        snsUniqueId = snsUniqueId
    )
}

fun LocationResponse.toModel(): LocationModel {
    val simpleAddress = StringBuilder()
    var isDetail = false
    address.split(" ").forEach { name ->
        if(name.isEmpty()) return@forEach
        if(name.first() == '(') isDetail = true
        if (isDetail) return@forEach

        val newName = when(name) {
            "대한민국", "경기도", "경기", "충청북도", "충북", "충청남도", "충남", "경상북도", "경북",
            "경상남도", "경남", "전라북도", "전북", "전라남도", "전남", "제주특별자치도" -> return@forEach
            "부산" -> "부산광역시"
            "대구" -> "대구광역시"
            "인천" -> "인천광역시"
            "광주" -> "광주광역시"
            "대전" -> "대전광역시"
            "울산" -> "울산광역시"
            "서울" -> "서울특별시"
            else -> name
        }

        simpleAddress.append(newName).append(" ")
    }

    return LocationModel(
        lat = lat,
        lng = lng,
        address = simpleAddress.toString()
    )
}

fun RegionCodeResponse.toModel(): RegionCodeModel {
    return RegionCodeModel(
        stanReginCd[1].row[0].regionCode
    )
}
