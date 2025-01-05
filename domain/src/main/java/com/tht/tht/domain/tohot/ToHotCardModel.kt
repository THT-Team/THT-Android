package com.tht.tht.domain.tohot

import com.tht.tht.domain.signup.model.IdealTypeModel
import com.tht.tht.domain.signup.model.InterestModel
import com.tht.tht.domain.topic.DailyTopicModel

sealed interface ToHotCardModel

data class DailyUserCardModel(
    val id: String,
    val nickname: String,
    val isBirthDay: Boolean,
    val interests: List<InterestModel>,
    val idealTypes: List<IdealTypeModel>,
    val age: Int,
    val address: String,
    val profileImgUrl: List<String>,
    val introduce: String,
    val userDailyFallingCourserIdx: Int
) : ToHotCardModel

data class DailyTopicListModel(
    val topicResetTimeMill: Long,
    val introduction: String,
    val topicSelectType: TopicSelectType,
    val topics: List<DailyTopicModel>
) : ToHotCardModel {
    enum class TopicSelectType {
        ONE_CHOICE,
        TWO_CHOICE,
        FOUR_CHOICE
    }
}
