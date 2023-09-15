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
import androidx.compose.ui.text.style.TextAlign
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
import tht.feature.chat.model.ChatListUiModel

@Composable
internal fun ChatItem(
    item: ChatListUiModel,
    isLoading: Boolean,
    onClickItem: () -> Unit
) {
    Row(
        modifier = Modifier
            .noRippleClickable { onClickItem() }
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = (16.5).dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ThtImage(
            modifier = Modifier.skeleton(visible = isLoading),
            src = item.partnerProfileUrl,
            size = DpSize(50.dp, 50.dp)
        )
        Spacer(space = 12.dp)
        Column(modifier = Modifier.weight(1f)) {
            ThtSubtitle2(
                modifier = Modifier.skeleton(visible = isLoading),
                text = item.partnerName,
                fontWeight = FontWeight.Medium,
                color = Color(0xFFF9FAFA)
            )
            Spacer(space = 2.dp)
            ThtP1(
                modifier = Modifier.skeleton(visible = isLoading),
                text = item.currentMessage,
                textAlign = TextAlign.Start,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8D8D8D)
            )
        }
        Column(horizontalAlignment = Alignment.End) {
            ThtCaption1(
                modifier = Modifier.skeleton(visible = isLoading),
                text = item.messageTime,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF8D8D8D)
            )
            Spacer(space = 10.dp)
            ChatAlert(number = 1)
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ChatItemPreivew() {
    ChatItem(
        item = ChatListUiModel(
            chatRoomIdx = 1L,
            partnerProfileUrl = "",
            partnerName = "스티치",
            messageTime = "",
            currentMessage = "안녕"
        ),
        isLoading = false,
        onClickItem = {}
    )
}
