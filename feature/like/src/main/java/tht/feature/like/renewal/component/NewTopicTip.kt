package tht.feature.like.renewal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.p.ThtP1

@Composable
@Preview
internal fun NewTopicTip(
    modifier: Modifier = Modifier,
    navigateMain: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .statusBarsPadding()
            .clip(RoundedCornerShape(30.dp))
            .background(Color(0xFF222222))
            .padding(horizontal = 17.dp, vertical = 14.dp)
            .clickable { navigateMain() }
    ) {
        ThtP1(
            text = "새로운 주제어가 오픈되었어요!",
            fontWeight = FontWeight.W400,
            color = Color.White
        )
        Spacer(9.dp)
        ThtP1(
            text = "메인화면으로 이동",
            fontWeight = FontWeight.SemiBold,
            color = Color(0xFFF9CC2E),
        )
    }
}
