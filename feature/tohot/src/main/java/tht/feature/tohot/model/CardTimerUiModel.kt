package tht.feature.tohot.model

import androidx.compose.runtime.Immutable
import kotlin.time.Duration

@Immutable
data class CardTimerUiModel(
    val maxTimer: Duration,
    val initialDelay: Duration,
    val completionDelay: Duration,
    val duration: Duration,
    val startAble: Boolean, // card image loading 이 완료 후 timer 실행을 위한 속성
    val timerType: ToHotTimer = ToHotTimer.Timer,
) {
    enum class ToHotTimer {
        Timer,
        Heart,
        Dislike
    }
}
