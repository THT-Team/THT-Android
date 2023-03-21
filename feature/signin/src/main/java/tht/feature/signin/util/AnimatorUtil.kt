package tht.feature.signin.util

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.view.View

object AnimatorUtil {
    fun hapticAnimation(view: View, itemDuration: Long = 50) {
        AnimatorSet().apply {
            playSequentially(
                listOf(
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -10f).setDuration(itemDuration),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 10f).setDuration(itemDuration),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -10f).setDuration(itemDuration),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f).setDuration(itemDuration),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -10f).setDuration(itemDuration),
                    ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0f).setDuration(itemDuration)
                )
            )
        }.start()
    }
}
