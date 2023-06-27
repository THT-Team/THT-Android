package tht.feature.tohot.model

import androidx.compose.runtime.Immutable


@Immutable
data class CardTimerUiModel(
    val maxSec: Int,
    val currentSec: Int,
    val destinationSec: Int,
    val startAble: Boolean
)
