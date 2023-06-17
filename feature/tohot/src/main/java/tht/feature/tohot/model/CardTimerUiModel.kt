package tht.feature.tohot.model

import javax.annotation.concurrent.Immutable

@Immutable
data class CardTimerUiModel(
    val maxSec: Int,
    val currentSec: Int,
    val destinationSec: Int,
    val startAble: Boolean
)
