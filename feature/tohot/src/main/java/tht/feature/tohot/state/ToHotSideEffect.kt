package tht.feature.tohot.state

sealed class ToHotSideEffect {
    data class ScrollToAndRemoveFirst(
        val scrollIdx: Int,
        val removeIdx: Int
    ) : ToHotSideEffect()
}
