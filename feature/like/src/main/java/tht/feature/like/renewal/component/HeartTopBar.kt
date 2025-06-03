package tht.feature.like.renewal.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4

@Composable
internal fun HeartTopAppBar(
    title: String,
    rightIcons: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = (15.5).dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ThtHeadline4(text = title, fontWeight = FontWeight.SemiBold, color = Color(0xFFF9FAFA))
        rightIcons()
    }
}
