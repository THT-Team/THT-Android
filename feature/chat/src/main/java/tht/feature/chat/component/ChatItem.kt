package tht.feature.chat.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.image.ThtImage
import com.example.compose_ui.component.modifier.skeleton
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import com.example.compose_ui.extensions.noRippleClickable

@Composable
internal fun ChatItem(
    item: String,
    isLoading: Boolean,
    onClickItem: () -> Unit,
) {
    Row(
        modifier = Modifier
            .noRippleClickable { onClickItem() }
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = (16.5).dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ThtImage(
            modifier = Modifier.skeleton(visible = isLoading),
            src = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEyMjJfMjYz%2FMDAxNjQwMTA3ODUyNzgy.2vrUEWwtR7K3P-TtNzfIsdCoM73Af9YPfpDLwq_iwMUg.D5PI3qGu_Q1tGN1HaZvFJX0dWqocJEk0AsnQ5zz1RGsg.JPEG.eeducator%2Fpexels-cottonbro-3663069.jpg&type=sc960_832", // ktlint-disable max-line-length
            size = DpSize(50.dp, 50.dp),
        )
        Spacer(space = 12.dp)
        Column(modifier = Modifier.weight(1f)) {
            ThtSubtitle2(
                modifier = Modifier.skeleton(visible = isLoading),
                text = item,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA),
            )
            Spacer(space = 2.dp)
            ThtP1(
                modifier = Modifier.skeleton(visible = isLoading),
                text = "새로운 메시지가 도착했습니다.",
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8D8D8D),
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            ThtCaption1(
                modifier = Modifier.skeleton(visible = isLoading),
                text = "08:24 PM",
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8D8D8D),
            )
            Spacer(space = 10.dp)
            ChatAlert(number = 1)
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ChatItemPreivew() {
    ChatItem(item = "아이템 1", isLoading = false, onClickItem = {})
}
