package tht.feature.signin.util

import android.content.Context
import android.util.DisplayMetrics

object SizeUtil {
    fun getPxFromDp(context: Context, dp: Int): Float {
        val displayMetrics = context.resources.displayMetrics
        return dp * (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }
}
