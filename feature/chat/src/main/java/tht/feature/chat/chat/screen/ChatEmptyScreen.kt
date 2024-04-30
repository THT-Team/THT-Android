package tht.feature.chat.chat.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.button.ThtButton
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import tht.feature.chat.R

@Composable
internal fun ChatEmptyScreen(
    onClickChangeTitle: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            ThtHeadline4(
                text = "아직 매칭된 무디가 없어요",
                fontWeight = FontWeight.W600,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(6.dp))
            ThtP1(
                text = "대화가 잘 통하는 무디를 찾아볼까요?",
                fontWeight = FontWeight.W400,
                color = Color(0xFF8D8D8D)
            )
        }
        Spacer(modifier = Modifier.height(64.dp))
        ThtButton(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .border(1.dp, Color.White, RoundedCornerShape(16.dp))
                .padding(horizontal = 70.dp),
            backgroundColor = Color.Transparent,
            contentColor = Color.White,
            onClick = onClickChangeTitle,
            content = {
                ThtHeadline5(
                    text = "무디들 만나러 가기",
                    fontWeight = FontWeight.W700,
                    color = Color.White
                )
            }
        )
        Spacer(modifier = Modifier.height(56.dp))
    }
}
