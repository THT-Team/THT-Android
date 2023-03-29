package tht.feature.signin.util

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView

object StringUtil {
    fun setWhiteTextColor(textView: TextView, range: IntRange) {
        textView.text = setTextColor(
            textView.text.toString(),
            range,
            textView.context.resources.getColor(
                tht.core.ui.R.color.white_f9fafa, null
            )
        )
    }

    private fun setTextColor(text: String, range: IntRange, color: Int): CharSequence {
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
