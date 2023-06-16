package tht.feature.tohot.state

import androidx.compose.runtime.Stable
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel

@Stable
data class ToHotState(
    val userList: ImmutableListWrapper<ToHotUserUiModel>,
    val timers: ImmutableListWrapper<CardTimerUiModel>,
    val enableTimerIdx: Int,
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
