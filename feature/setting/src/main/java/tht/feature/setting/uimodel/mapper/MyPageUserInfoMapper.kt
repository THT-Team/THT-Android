package tht.feature.setting.uimodel.mapper

import com.tht.tht.domain.setting.model.MyPageUserInfoModel
import kotlinx.collections.immutable.toPersistentList
import tht.core.ui.util.StringUtil
import tht.feature.setting.uimodel.MyPageUserInfoUiModel

fun MyPageUserInfoModel.toUiModel(): MyPageUserInfoUiModel {
    return MyPageUserInfoUiModel(
        userUuid = userUuid,
        username = username,
        userProfilePhotos = userProfilePhotos.map { it.toUiModel() }.toPersistentList(),
        birth = birth,
        gender = when (gender) {
            "MALE" -> MyPageUserInfoUiModel.Gender.Male
            "FEMALE" -> MyPageUserInfoUiModel.Gender.FeMale
            else -> MyPageUserInfoUiModel.Gender.UnKnown
        },
        preferredGender = when (preferredGender) {
            "MALE" -> MyPageUserInfoUiModel.Gender.Male
            "FEMALE" -> MyPageUserInfoUiModel.Gender.FeMale
            else -> MyPageUserInfoUiModel.Gender.UnKnown
        },
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
        emojiCode = StringUtil.parseEmoji(emojiCode),
        idx = idx,
        name = name
    )
}


fun MyPageUserInfoModel.Interests.toUiModel(): MyPageUserInfoUiModel.Interests {
    return MyPageUserInfoUiModel.Interests(
        emojiCode = StringUtil.parseEmoji(emojiCode),
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
