package tht.feature.tohot.state

import androidx.compose.runtime.Stable
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel

@Stable
data class ToHotState(
    val userList: ImmutableListWrapper<ToHotUserUiModel>,
    val timers: ImmutableListWrapper<CardTimerUiModel>,
    val enableTimerIdx: Int
)
