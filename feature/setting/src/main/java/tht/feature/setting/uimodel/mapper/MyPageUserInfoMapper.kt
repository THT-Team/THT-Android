package tht.feature.setting.uimodel.mapper

import com.tht.tht.domain.setting.model.MyPageUserInfoModel
import kotlinx.collections.immutable.toPersistentList
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

fun MyPageUserInfoModel.toUiModel(): MyPageUserInfoUiModel {
    return MyPageUserInfoUiModel(
        userUuid = userUuid,
        username = username,
        userProfilePhotos = userProfilePhotos.map { it.toUiModel() }.toPersistentList(),
        birth = birth,
        gender = gender,
        preferredGender = preferredGender,
        introduction = introduction,
        smoke = smoke,
        drink = drink,
        height = height,
        religion = religion,
        idealTypeList = idealTypeList.map { it.toUiModel() }.toPersistentList(),
        interestsList = interestsList.map { it.toUiModel() }.toPersistentList(),
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
