package tht.feature.tohot.model

import androidx.compose.runtime.Immutable

@Immutable
data class CardTimerUiModel(
    val maxSec: Int,
    val currentSec: Float,
    val destinationSec: Float,
    val startAble: Boolean, // card image loading 이 완료 후 timer 실행을 위한 속성
    val timerType: ToHotTimer = ToHotTimer.Timer
) {
    enum class ToHotTimer {
        Timer,
        Heart,
        Dislike
    }
}
