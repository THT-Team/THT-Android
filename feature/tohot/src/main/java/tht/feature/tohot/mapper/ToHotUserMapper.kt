package tht.feature.tohot.mapper

import com.tht.tht.domain.tohot.DailyTopicListModel
import com.tht.tht.domain.tohot.DailyUserCardListModel
import com.tht.tht.domain.tohot.DailyUserCardModel
import com.tht.tht.domain.tohot.ToHotCardModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotCardUiModel
import tht.feature.tohot.model.ToHotUserUiModel

fun ToHotCardModel.toUiModel(): ToHotCardUiModel {
    return when (this) {
        is DailyUserCardModel -> {
            ToHotCardUiModel.User(this.toUiModel())
        }
        is DailyTopicListModel -> {
            ToHotCardUiModel.Topic(this.toUiModel())
        }
    }
}

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

fun List<DailyUserCardModel>.toCardUiModel(): List<ToHotCardUiModel> {
    return this.map { ToHotCardUiModel.User(it.toUiModel()) }
}

fun DailyUserCardListModel.toCardUiModel(): List<ToHotCardUiModel> {
    return this.cards.toCardUiModel()
}
