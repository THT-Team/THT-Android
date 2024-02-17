package tht.feature.setting.uimodel.mapper

import com.tht.tht.domain.setting.model.MyPageUserInfoModel
import kotlinx.collections.immutable.toPersistentList
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

fun MyPageUserInfoModel.toUiModel(): MyPageUserInfoUiModel {
    return MyPageUserInfoUiModel(
        address = address,
        age = age,
        email = email,
        idealTypeList = idealTypeList.map { it.toUiModel() }.toPersistentList(),
        interestsList = interestsList.map { it.toUiModel() }.toPersistentList(),
        introduction = introduction,
        phoneNumber = phoneNumber,
        userProfilePhotos = userProfilePhotos.map { it.toUiModel() }.toPersistentList(),
        userUuid = userUuid,
        username = username
    )
}

fun MyPageUserInfoModel.IdealType.toUiModel(): MyPageUserInfoUiModel.IdealType {
    return MyPageUserInfoUiModel.IdealType(
        emojiCode = emojiCode,
        idx = idx,
        name = name
    )
}


fun MyPageUserInfoModel.Interests.toUiModel(): MyPageUserInfoUiModel.Interests {
    return MyPageUserInfoUiModel.Interests(
        emojiCode = emojiCode,
        idx = idx,
        name = name
    )
}

fun MyPageUserInfoModel.UserProfilePhoto.toUiModel(): MyPageUserInfoUiModel.UserProfilePhoto {
    return MyPageUserInfoUiModel.UserProfilePhoto(
        priority = priority,
        url = url
    )
}
