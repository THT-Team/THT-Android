package tht.feature.chat.component.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.chat.R

@Composable
internal fun ChatDetailTopAppBar(
    title: String,
    onClickBack: () -> Unit,
    onClickReport: () -> Unit,
    onClickLogout: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 9.dp)
    ) {
        Image(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .noRippleClickable { onClickBack() },
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "뒤로가기 버튼"
        )
        Column(
            modifier = Modifier
                .border(1.dp, Color(0xFF2D2D2D), RoundedCornerShape(24.dp))
                .clip(RoundedCornerShape(24.dp))
                .align(Alignment.Center)
                .background(Color(0xFF252525))
                .padding(top = 4.dp, bottom = 2.dp, start = 33.dp, end = 33.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ThtCaption1(text = "우리가 통한 주제", fontWeight = FontWeight.Normal, color = Color.White)
            ThtP2(text = title, fontWeight = FontWeight.SemiBold, color = Color(0xFFF9CC2E))
        }
        Row(
            modifier = Modifier
                .align(Alignment.CenterEnd)
        ) {
            Image(
                modifier = Modifier
                    .noRippleClickable { onClickReport() },
                painter = painterResource(id = R.drawable.ic_report),
                contentDescription = "신고하기 버튼"
            )
            Spacer(space = 20.dp)
            Image(
                modifier = Modifier
                    .noRippleClickable { onClickLogout() },
                painter = painterResource(id = R.drawable.ic_logout),
                contentDescription = "로그아웃 버튼"
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ChatTopAppBarPreview() {
    ChatDetailTopAppBar(title = "채팅", onClickBack = {}, onClickLogout = {}, onClickReport = {})
}
