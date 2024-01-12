package tht.feature.tohot.mapper

import com.tht.tht.domain.dailyusercard.DailyUserCardModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel

fun DailyUserCardModel.toUiModel(): ToHotUserUiModel {
    return ToHotUserUiModel(
        id = id,
        idx = userDailyFallingCourserIdx,
        nickname = nickname,
        isBirthday = isBirthDay,
        interests = ImmutableListWrapper(interests),
        idealTypes = ImmutableListWrapper(idealTypes),
        age = age,
        address = address,
        profileImgUrl = ImmutableListWrapper(profileImgUrl),
        introduce = introduce
    )
}
