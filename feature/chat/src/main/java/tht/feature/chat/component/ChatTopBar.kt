package tht.feature.chat.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import tht.feature.chat.R

@Composable
internal fun ChatTopAppBar(
    title: String,
    rightIcons: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = (15.5).dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        ThtHeadline4(text = title, fontWeight = FontWeight.SemiBold, color = Color(0xFFF9FAFA))
        rightIcons()
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ChatTopAppBarPreview() {
    ChatTopAppBar(title = "채팅") {
        Icon(
            tint = Color.White,
            painter = painterResource(id = com.example.compose_ui.R.drawable.ic_non_alert),
            contentDescription = null
        )
    }
}
