package tht.feature.tohot.component.chip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import tht.feature.tohot.component.userinfo.ToHotUserInfoFullCard

@Composable
fun ToHotChip(
    modifier: Modifier = Modifier,
    item: String
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = Color(0xFF111111).copy(alpha = 0.5f)
            )
    ) {
        ThtP2(
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 8.dp),
            text = item,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFF9FAFA)
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
private fun ToHotChipPreview() {
    ToHotChip(
        item = "chip"
    )
}
