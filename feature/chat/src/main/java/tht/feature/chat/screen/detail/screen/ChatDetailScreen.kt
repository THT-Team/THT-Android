package tht.feature.chat.screen.detail.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import tht.feature.chat.screen.detail.component.ChatDetailTopAppBar

@Composable
internal fun ChatDetailScreen() {
    ChatDetailTopAppBar(
        title = "마음",
        onClickBack = { },
        onClickReport = {},
        onClickLogout = {},
    )
    Column(modifier = Modifier.fillMaxSize()) {
        ThtSubtitle2(
            text = "상세화면",
            fontWeight = FontWeight.Medium,
            color = Color(0xFFF9FAFA),
        )
    }
}
