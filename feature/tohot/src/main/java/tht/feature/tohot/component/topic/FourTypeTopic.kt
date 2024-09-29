package tht.feature.tohot.component.topic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP1
import com.example.compose_ui.component.text.p.ThtP2
import tht.core.ui.R

@Composable
fun FourTypeTopic(
    hasSelectTopic: Boolean,
    iconUrl: String?,
    @DrawableRes iconRes: Int,
    title: String,
    key: Int, // key 네이밍이지만 topic ui model 의 idx 속성을 넣어줌
    content: String,
    isSelect: Boolean,
    modifier: Modifier = Modifier,
    iconSize: Dp = 20.dp,
    topicClickListener: (Int) -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(56.dp))
            .background(
                color = colorResource(id = R.color.black_161616)
            )
            .clickable { topicClickListener(key) }
            .border(
                width = 1.dp,
                color = if (isSelect) {
                    colorResource(id = R.color.yellow_f9cc2e)
                } else {
                    colorResource(R.color.black_222222)
                },
                shape = RoundedCornerShape(56.dp)
            )
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .alpha(if (isSelect || !hasSelectTopic) 100f else 0.4f),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TopicItemChipImage(
                modifier = Modifier.size(iconSize),
                imageUrl = iconUrl,
                error = painterResource(id = iconRes)
            )
            Spacer(modifier = Modifier.width(2.dp))
            ThtP2(
                text = title,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.gray_8d8d8d)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        ThtP1(
            modifier = Modifier.fillMaxWidth(),
            text = content,
            fontWeight = FontWeight.Medium,
            color = colorResource(id = R.color.white_f9fafa),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
@Preview
private fun TopicSelectItemPreview() {
    FourTypeTopic(
        iconUrl = null,
        iconRes = tht.feature.tohot.R.drawable.ic_topic_item_fun_48,
        title = "게임",
        content = "게임 좋아하시나요?",
        key = 1,
        isSelect = false,
        hasSelectTopic = true
    )
}

@Composable
@Preview
private fun SelectTopicSelectItemPreview() {
    FourTypeTopic(
        iconUrl = null,
        iconRes = tht.feature.tohot.R.drawable.ic_topic_item_fun_48,
        title = "게임",
        content = "게임 좋아하시나요?게임 좋아하시나요?게임 좋아하시나요?게임 좋아하시나요?",
        key = 1,
        isSelect = true,
        hasSelectTopic = true
    )
}

@Composable
@Preview
private fun SelectTopicSelectItemNoneSelectPreview() {
    FourTypeTopic(
        iconUrl = null,
        iconRes = tht.feature.tohot.R.drawable.ic_topic_item_fun_48,
        title = "게임",
        content = "게임 좋아하시나요?게임 좋아하시나요?게임 좋아하시나요?게임 좋아하시나요?",
        key = 1,
        isSelect = true,
        hasSelectTopic = false
    )
}
