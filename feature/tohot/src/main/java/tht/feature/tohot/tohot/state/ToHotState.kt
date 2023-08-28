package tht.feature.tohot.tohot.state

import androidx.compose.runtime.Immutable
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.MatchingUserUiModel
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.TopicUiModel

@Immutable
data class ToHotState(
    val loading: ToHotLoading,
    val userList: ImmutableListWrapper<ToHotUserUiModel>,
    val userCardState: ToHotCardState, // Empty 일 경우 보여줄 View 를 정함
    val timers: ImmutableListWrapper<CardTimerUiModel>,
    val enableTimerIdx: Int, // 현재 표시 되는 Card Idx -> 해당 Card 의 Timer 진행됨
    val fallingAnimationIdx: Int = -1, // 신고, 차단 Animation Idx
    val cardMoveAllow: Boolean, // card suspend 기능. false 일 경우 Timer 중단. Dialog 등이 표시 될 때 사용
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
    val selectTopicKey: Int = -1,
    val currentTopic: TopicUiModel?,
    val topicList: ImmutableListWrapper<TopicUiModel>,
    val topicModalShow: Boolean,
    val topicResetRemainingTime: String,
    val topicResetTimeMill: Long,
    val hasUnReadAlarm: Boolean,
    val matchingFullScreenUser: MatchingUserUiModel? = null
)

enum class ToHotLoading {
    None,
    TopicList,
    TopicSelect,
    UserList,
    Report,
    Block,
    Heart,
    Dislike
}

enum class ToHotCardState {
    Initialize,
    Running,
    Error
}