package tht.feature.tohot.component.animation

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.tween

suspend fun shakingAnimation(
    rotateAngle: Animatable<Float, AnimationVector1D>,
    animateAngle: Float,
    totalDuration: Int = 1000,
    repeatCount: Int,
    compilation: () -> Unit = { }
) {
    val duration = totalDuration / (repeatCount * 2)
    val default = rotateAngle.value
    repeat(repeatCount) {
        rotateAngle.animateTo(
            targetValue = default + animateAngle,
            animationSpec = tween(
                durationMillis = duration,
                delayMillis = 0
            )
        )
        rotateAngle.animateTo(
            targetValue = default - animateAngle,
            animationSpec = tween(
                durationMillis = duration,
                delayMillis = 0
            )
        )
    }
    rotateAngle.animateTo(
        targetValue = default,
        animationSpec = tween(
            durationMillis = duration,
            delayMillis = 0
        )
    )
    compilation()
}
