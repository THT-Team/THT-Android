package tht.feature.tohot.state

sealed class ToHotSideEffect {
    data class ToastMessage(
        val message: String
    ) : ToHotSideEffect()

    data class Scroll(
        val idx: Int
    ) : ToHotSideEffect()

    data class RemoveAfterScroll(
        val scrollIdx: Int,
        val removeIdx: Int
    ) : ToHotSideEffect()

    data class UserHeart(
        val userIdx: Int
    ) : ToHotSideEffect()
    data class UserDislike(
        val userIdx: Int
    ) : ToHotSideEffect()
}
