package com.example.compose_ui.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2

/**
 * decode 를 해서 전달주는 곳이 있고, 아닌곳이 있음
 * decode 를 해서 전달주는 곳
 * - MyPageInfoRows
 *
 * 모르는곳
 * - ToHotUserInfoFullCard
 *
 * TODO: 모두 decode 해서 전달주도록?
 */
@Composable
fun ToHotEmojiChip(
    modifier: Modifier = Modifier,
    content: String,
    emojiCode: String
) {
    val emoji = runCatching {
        val code = Integer.decode("0x$emojiCode")
        String(Character.toChars(code))
    }.getOrNull() ?: emojiCode
    ToHotChip(
        modifier = modifier,
        item = "$emoji $content"
    )
}

@Composable
fun ToHotChip(
    modifier: Modifier = Modifier,
    item: String
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(24.dp))
            .background(
                color = Color(0xFF111111)
            )
    ) {
        ThtP2(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = item,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFF9FAFA),
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "emojiChip")
private fun ToHotEmojiChipPreview() {
    ToHotEmojiChip(
        content = "content",
        emojiCode = "1F3E0"
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, name = "chip")
private fun ToHotChipPreview() {
    ToHotChip(
        item = "chip"
    )
}
