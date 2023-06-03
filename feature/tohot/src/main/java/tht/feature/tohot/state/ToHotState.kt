package tht.feature.tohot.state

import androidx.compose.runtime.Immutable
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.TopicUiModel

@Immutable
data class ToHotState(
    val userList: ImmutableListWrapper<ToHotUserUiModel>,
    val timers: ImmutableListWrapper<CardTimerUiModel>,
    val enableTimerIdx: Int,
    val selectTopic: TopicUiModel?,
    val topicList: ImmutableListWrapper<TopicUiModel>,
    val topicSelectRemainingTime: String
)
