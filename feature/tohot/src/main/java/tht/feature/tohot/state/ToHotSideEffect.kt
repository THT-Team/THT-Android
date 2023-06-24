package tht.feature.tohot.state

sealed class ToHotSideEffect {
    data class ToastMessage(
        val message: String
    ) : ToHotSideEffect()

    data class RemoveAndScroll(
        val scrollIdx: Int,
        val removeIdx: Int
    ) : ToHotSideEffect()
}
