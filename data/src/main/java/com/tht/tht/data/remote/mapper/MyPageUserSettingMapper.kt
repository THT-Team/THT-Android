package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.setting.MyPageUserInfoResponse
import com.tht.tht.domain.setting.model.MyPageUserInfoModel

fun MyPageUserInfoResponse.toModel(): MyPageUserInfoModel {
    return MyPageUserInfoModel(
        address = address,
        age = age,
        email = email,
        idealTypeList = idealTypeList.map { it.toModel() },
        interestsList = interestsList.map { it.toModel() },
        introduction = introduction,
        phoneNumber = phoneNumber,
        userProfilePhotos = userProfilePhotos.map { it.toModel() },
        userUuid = userUuid,
        username = username
    )
}

fun MyPageUserInfoResponse.IdealType.toModel(): MyPageUserInfoModel.IdealType {
    return MyPageUserInfoModel.IdealType(
        emojiCode = emojiCode,
        idx = idx,
        name = name
    )
}


fun MyPageUserInfoResponse.Interests.toModel(): MyPageUserInfoModel.Interests {
    return MyPageUserInfoModel.Interests(
        emojiCode = emojiCode,
        idx = idx,
        name = name
    )
}

fun MyPageUserInfoResponse.UserProfilePhoto.toModel(): MyPageUserInfoModel.UserProfilePhoto {
    return MyPageUserInfoModel.UserProfilePhoto(
        priority = priority,
        url = url
    )
}
