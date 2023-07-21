package tht.feature.tohot.model

import androidx.compose.runtime.Immutable

@Immutable
data class CardTimerUiModel(
    val maxSec: Int,
    val currentSec: Int,
    val destinationSec: Int,
    val startAble: Boolean // card image loading 이 완료 후 timer 실행을 위한 속성
)
