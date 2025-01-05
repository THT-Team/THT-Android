package tht.feature.tohot.tohot.state

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableList
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.MatchingUserUiModel
import tht.feature.tohot.model.ToHotCardUiModel
import tht.feature.tohot.model.TopicUiModel

@Immutable
data class ToHotState(
    val loading: ToHotLoading,
    val cardList: ImmutableList<ToHotCardUiModel>,
    val userCardState: ToHotCardState = ToHotCardState.NoneSelectTopic, // Start, Empty 경우 보여줄 View 를 정함
    val timer: CardTimerUiModel, // 현재 표시 중인 Card TimerState
    val enableTimerIdx: Int, // 현재 표시 되는 Card Idx -> 해당 Card 의 Timer 진행됨
    val dialogState: DialogState,
    val cardVisibleState: CardVisibleState,
    val topic: TopicInfo,
    val hasUnReadAlarm: Boolean,
    val loginAvailable: Boolean = true // 로그인 유효성
) {
    @Immutable
    data class CardVisibleState(
        val cardMoveAllow: Boolean, // card suspend 기능. false 일 경우 Timer 중단. Dialog 등이 표시 될 때 사용
        val fallingAnimationIdx: Int = -1, // 신고, 차단 Animation Idx
        val holdCard: Boolean = false, // 일시정지 카드
        val shakingCard: Boolean = false, // 흔들다리 효과 애니메이션. 3초에 시작
        val matchingFullScreenUser: MatchingUserUiModel? = null
    )

    @Immutable
    data class DialogState(
        val reportMenuDialogShow: Boolean = false,
        val reportDialogShow: Boolean = false,
        val blockDialogShow: Boolean = false,
        val reportReason: List<String> = listOf(
            "불쾌한 사진",
            "허위 프로필",
            "사진 도용",
            "욕설 및 비방",
            "불법 촬영물 공유"
        )
    )

    @Immutable
    data class TopicInfo(
        val selectTopicIdx: Int = -1,
        val topicResetTimeMill: Long = 0,
        val currentTopic: TopicUiModel? = null
    )
}

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
    NoneSelectTopic, // Topic 선택 이전
    Enter, // Topic 선택 후 앱 접속
    NoneInitializeUser, // Topic 선택 후, 유저가 없는 경우
    QuerySuccess, // 새로운 유저 조회 성공
    Running, // 정상 동작
    Error
}
