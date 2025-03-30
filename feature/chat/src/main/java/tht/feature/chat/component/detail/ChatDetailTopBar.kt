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
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.chat.R

@Composable
internal fun ChatDetailTopAppBar(
    title: String,
    onClickBack: () -> Unit,
    onClickReport: () -> Unit,
    onClickLogout: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = (15.5).dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .noRippleClickable { onClickBack() },
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = "뒤로가기 버튼"
            )
            Spacer(12.dp)
            ThtHeadline4(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFFF9FAFA)
            )
        }
        Spacer(space = 20.dp)
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

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ChatDetailTopAppBarPreview() {
    ChatDetailTopAppBar(title = "닉네임의라믄이름와요으아러야아르아어랴여래으랴열", onClickBack = {}, onClickLogout = {}, onClickReport = {})
}
