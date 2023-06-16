package tht.feature.tohot.component.emoji

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_ui.extensions.dpTextUnit

@Composable
fun EmojiText(
    modifier: Modifier = Modifier,
    emojiCode: String,
    textSize: Int,
) {
    val code = Integer.decode("0x${emojiCode}")
    val emoji = String(Character.toChars(code))
    Text(
        modifier = modifier,
        text = emoji,
        fontSize = textSize.dpTextUnit
    )
}

@Composable
@Preview
fun EmojiTextPreview() {
    EmojiText(
        emojiCode = "1F4DA",
        textSize = 38
    )
}
