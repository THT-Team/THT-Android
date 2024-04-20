package com.tht.tht.data.remote.mapper

import com.tht.tht.data.remote.response.setting.MyPageUserInfoResponse
import com.tht.tht.domain.setting.model.MyPageUserInfoModel

fun MyPageUserInfoResponse.toModel(): MyPageUserInfoModel {
    return MyPageUserInfoModel(
        userUuid = userUuid,
        username = username,
        birth = "UnKnown",
        gender = gender,
        introduction = introduction,
        preferredGender = preferGender,
        height = tall,
        smoke = smoking,
        drink = drinking,
        religion = religion,
        userProfilePhotos = userProfilePhotos.map { it.toModel() },
        idealTypeList = idealTypeList.map { it.toModel() },
        interestsList = interestsList.map { it.toModel() }
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
