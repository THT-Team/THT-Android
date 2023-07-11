package tht.feature.tohot.component.topic

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import tht.core.ui.R

@Composable
fun TopicSelectChip(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    @DrawableRes imageRes: Int,
    title: String,
    key: Int,
    content: String,
    isSelect: Boolean,
    topicClickListener: (Int) -> Unit = { }
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(36.dp))
            .background(
                color = colorResource(id = R.color.black_3d3d3d)
            )
            .clickable { topicClickListener(key) }
            .border(
                width = 1.dp,
                color = if (isSelect) colorResource(id = R.color.yellow_f9cc2e) else Color.Transparent,
                shape = RoundedCornerShape(36.dp)
            )
    ) {
        TopicItemChipImage(
            modifier = Modifier
                .padding(start = 24.dp, top = 16.dp, bottom = 16.dp)
                .align(Alignment.CenterVertically),
            imageUrl = imageUrl,
            error = painterResource(id = imageRes)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, top = 16.dp, bottom = 16.dp, end = 14.dp)
                .align(Alignment.CenterVertically)
        ) {
            ThtSubtitle2(
                text = title,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = R.color.gray_8d8d8d)
            )

            ThtP2(
                modifier = Modifier.padding(top = 4.dp),
                text = content,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.white_f9fafa)
            )
        }
    }
}

@Composable
@Preview
private fun TopicSelectItemPreview() {
    TopicSelectChip(
        imageUrl = "1F3AE",
        imageRes = tht.feature.tohot.R.drawable.ic_topic_item_fun_48,
        title = "게임",
        content = "게임 좋아하시나요?",
        key = 1,
        isSelect = false
    )
}

@Composable
@Preview
private fun SelectTopicSelectItemPreview() {
    TopicSelectChip(
        imageUrl = "1F3AE",
        imageRes = tht.feature.tohot.R.drawable.ic_topic_item_fun_48,
        title = "게임",
        content = "게임 좋아하시나요?",
        key = 1,
        isSelect = true
    )
}
