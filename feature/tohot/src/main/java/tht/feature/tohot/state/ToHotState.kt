package tht.feature.tohot.state

import androidx.compose.runtime.Immutable
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.TopicUiModel

@Immutable
data class ToHotState(
    val loading: Boolean,
    val userList: ImmutableListWrapper<ToHotUserUiModel>,
    val isFirstPage: Boolean = true,
    val timers: ImmutableListWrapper<CardTimerUiModel>,
    val enableTimerIdx: Int,
    val fallingAnimationIdx: Int = -1,
    val cardMoveAllow: Boolean,
    val reportMenuDialogShow: Boolean = false,
    val reportDialogShow: Boolean = false,
    val blockDialogShow: Boolean = false,
    val holdDialogShow: Boolean = false,
    val reportReason: List<String> = listOf(
        "불쾌한 사진",
        "허위 프로필",
        "사진 도용",
        "욕설 및 비방",
        "불법 촬영물 공유"
    ),
    val selectTopicKey: Long = -1,
    val currentTopic: TopicUiModel?,
    val topicList: ImmutableListWrapper<TopicUiModel>,
    val topicModalShow: Boolean,
    val topicSelectRemainingTime: String,
    val topicSelectRemainingTimeMill: Long,
    val hasUnReadAlarm: Boolean
)
