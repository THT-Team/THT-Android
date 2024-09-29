package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline3
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.noRippleClickable
import tht.core.ui.R
import tht.feature.tohot.component.topic.TopicItemChipImage
import tht.feature.tohot.model.TopicSelectUiModel
import tht.feature.tohot.model.TopicUiModel
import tht.feature.tohot.model.dummyTopics
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun TwoTypeTopicSelectCard(
    topicSelectUiModel: TopicSelectUiModel.TwoTopic,
    selectTopicKey: Int,
    buttonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { }
) {
    val hasSelectTopic by remember(selectTopicKey, topicSelectUiModel) {
         mutableStateOf(
             topicSelectUiModel.topic1.key == selectTopicKey ||
             topicSelectUiModel.topic2.key == selectTopicKey
         )
    }
    TopicSelectTypeCardScreen(
        modifier = modifier,
        topicExpiredDuration = topicSelectUiModel.topicExpiredDuration,
        introduce = topicSelectUiModel.introduce,
        buttonEnabled = buttonEnabled,
        onClickConfirm = onClickConfirm,
        isEvent = false
    ) {
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(17.dp)
            ) {
                TwoTypeTopic(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .noRippleClickable { onSelectTopic(topicSelectUiModel.topic1.key) },
                    hasSelectTopic = hasSelectTopic,
                    isSelect = selectTopicKey == topicSelectUiModel.topic1.key,
                    topicUiModel = topicSelectUiModel.topic1
                )
                TwoTypeTopic(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                        .noRippleClickable { onSelectTopic(topicSelectUiModel.topic2.key) },
                    isSelect = selectTopicKey == topicSelectUiModel.topic2.key,
                    hasSelectTopic = hasSelectTopic,
                    topicUiModel = topicSelectUiModel.topic2
                )
            }
            Box(
                modifier = Modifier.align(Alignment.Center)
                    .background(
                        color = colorResource(R.color.black_161616),
                        shape = RoundedCornerShape(size = 61.dp)
                    )
                    .padding(10.dp)
            ) {
                ThtHeadline3(
                    modifier = Modifier.align(Alignment.Center),
                    text = "VS",
                    fontWeight = FontWeight.Bold,
                    color = colorResource(R.color.yellow_f9cc2e)
                )
            }
        }
    }
}

@Composable
private fun TwoTypeTopic(
    hasSelectTopic: Boolean,
    isSelect: Boolean,
    topicUiModel: TopicUiModel,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = if (isSelect) {
                    colorResource(R.color.yellow_f9cc2e)
                } else {
                    colorResource(R.color.black_222222)
                },
                shape = RoundedCornerShape(size = 24.dp)
            )
            .background(
                color = colorResource(R.color.black_161616),
                shape = RoundedCornerShape(size = 24.dp)
            )
            .padding(horizontal = 24.dp, vertical = 12.dp)
            .alpha(if (isSelect || !hasSelectTopic) 100f else 0.4f),
        verticalArrangement = Arrangement.Center
    ) {
        TopicItemChipImage(
            modifier = Modifier
                .alpha(0.8f)
                .padding(1.5.dp)
                .size(72.dp)
                .align(Alignment.CenterHorizontally),
            imageUrl = topicUiModel.iconUrl,
            error = painterResource(id = topicUiModel.iconRes)
        )
        Spacer(modifier = Modifier.height(16.dp))
        ThtP2(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = topicUiModel.title,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.gray_8d8d8d)
        )
        ThtHeadline5(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = topicUiModel.content,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.white_f9fafa)
        )
    }
}

@Composable
@Preview
private fun TwoTypeTopicSelectCardPreview() {
    TwoTypeTopicSelectCard(
        topicSelectUiModel = TopicSelectUiModel.TwoTopic(
            introduce = "안녕하세요",
            topicExpiredDuration = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS),
            topic1 = dummyTopics[0],
            topic2 = dummyTopics[1],
        ),
        selectTopicKey = 0,
        buttonEnabled = true
    )
}

@Composable
@Preview
private fun TwoTypeTopicSelectCardNoneSelectPreview() {
    TwoTypeTopicSelectCard(
        topicSelectUiModel = TopicSelectUiModel.TwoTopic(
            introduce = "안녕하세요",
            topicExpiredDuration = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS),
            topic1 = dummyTopics[0],
            topic2 = dummyTopics[1],
        ),
        selectTopicKey = -1,
        buttonEnabled = true
    )
}
