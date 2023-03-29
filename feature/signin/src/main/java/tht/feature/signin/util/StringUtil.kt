package tht.feature.signin.util

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan

object StringUtil {
    fun setTextColor(text: String, range: IntRange, color: Int): CharSequence {
        return SpannableStringBuilder(text).apply {
            setSpan(
                ForegroundColorSpan(color),
                range.first,
                range.last + 1,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
}
