package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.request.signup.SignupRequest
import com.tht.tht.data.remote.response.ideal.IdealTypeResponse
import com.tht.tht.data.remote.response.interests.InterestTypeResponse
import com.tht.tht.data.remote.response.login.FcmTokenLoginResponse
import com.tht.tht.data.remote.response.signup.SignupResponse
import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.signup.model.SignupUserModel
import com.tht.tht.domain.signup.model.SignupResponseModel
import com.tht.tht.domain.token.model.FcmTokenLoginResponseModel

fun InterestTypeResponse.toModel(): InterestModel {
    return InterestModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}

fun IdealTypeResponse.toModel(): IdealTypeModel {
    return IdealTypeModel(
        title = name,
        key = idx,
        emojiCode = emojiCode
    )
}

fun SignupUserModel.toRemoteRequest(): SignupRequest {
    var agreement = SignupRequest.Agreement(
        locationServiceAgree = false,
        marketingAgree = false,
        personalPrivacyInfoAgree = false,
        serviceUseAgree = false
    )
    termsAgreement.keys.forEach {
        agreement = when (it.key) {
            "serviceUseAgree" ->
                agreement.copy(serviceUseAgree = termsAgreement.getOrDefault(it, false))

            "personalPrivacyInfoAgree" ->
                agreement.copy(personalPrivacyInfoAgree = termsAgreement.getOrDefault(it, false))

            "locationServiceAgree" ->
                agreement.copy(locationServiceAgree = termsAgreement.getOrDefault(it, false))

            "marketingAgree" ->
                agreement.copy(marketingAgree = termsAgreement.getOrDefault(it, false))

            else -> agreement
        }
    }
    return SignupRequest(
        birthDay = birthday,
        phoneNumber = phone,
        username = nickname,
        email = email,
        gender = gender,
        preferGender = preferredGender,
        introduction = introduce,
        deviceKey = "", // 임시 코드
        agreement = agreement,
        locationRequest = SignupRequest.LocationRequest(
            address = address,
            lat = lat,
            lon = lng,
            regionCode = 0 // 임시 코드
        ),
        photoList = profileImgUrl,
        interestList = interestKeys.map { it.toLong() }, // 임시 코드
        idealTypeList = idealTypeKeys,
        fcmToken = fcmToken,
        snsType = snsType,
        snsUniqueId = snsUniqueId
    )
}

fun SignupResponse.toModel(): SignupResponseModel {
    return SignupResponseModel(
        accessToken = accessToken,
        accessTokenExpiresIn = accessTokenExpiresIn
    )
}

fun FcmTokenLoginResponse.toModel(): FcmTokenLoginResponseModel {
    return FcmTokenLoginResponseModel(
        accessToken = accessToken,
        accessTokenExpiresIn = accessTokenExpiresIn
    )
}
