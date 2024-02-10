package tht.feature.signin.util

import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.widget.TextView

/**
 * https://apps.timwhitlock.info/emoji/tables/unicode
 * https://velog.io/@nagosooo/Textview%EC%97%90-Emoji-%EB%84%A3%EA%B8%B0
 * U+1F60A 이모지 유니코드 앞의 U+를 0x로 변환
 * 기존 Api에서 U+를 제거하고 보내줬으니, 앞에 0x만 붙이면 됨
 */
object StringUtil {
    fun parseEmoji(emojiCode: String): String? {
        return try {
            val code = Integer.decode("0x${emojiCode.removePrefix("U+")}")
            String(Character.toChars(code))
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setWhiteTextColor(textView: TextView, range: IntRange) {
        textView.text = setTextColor(
            textView.text.toString(),
            range,
            textView.context.resources.getColor(
                tht.core.ui.R.color.white_f9fafa,
                null
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
