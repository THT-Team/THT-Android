package tht.feature.chat.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.caption.ThtCaption1

@Composable
internal fun ChatAlert(
    number: Int,
) {
    Box(
        modifier = Modifier
            .size(18.dp)
            .clip(RoundedCornerShape(size = 50.dp))
            .background(Color(0xFFEF4444)),
        contentAlignment = Alignment.Center,
    ) {
        ThtCaption1(text = "$number", fontWeight = FontWeight.Normal, color = Color(0xFFF9FAFA))
    }
}

@Composable
@Preview
internal fun ChatAlertPreview() {
    ChatAlert(number = 1)
}
