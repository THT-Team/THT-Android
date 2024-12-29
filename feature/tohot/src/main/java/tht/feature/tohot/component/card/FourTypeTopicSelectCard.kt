package tht.feature.tohot.component.card

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.feature.tohot.component.topic.FourTypeTopic
import tht.feature.tohot.model.TopicSelectUiModel
import tht.feature.tohot.model.dummyTopics
import kotlin.time.DurationUnit
import kotlin.time.toDuration

@Composable
fun FourTypeTopicSelectCard(
    topicSelectUiModel: TopicSelectUiModel.FourTopic,
    selectTopicIdx: Int,
    buttonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { }
) {
    val hasSelectTopic by remember(topicSelectUiModel, selectTopicIdx) {
        mutableStateOf(topicSelectUiModel.topics.any { it.idx == selectTopicIdx })
    }
    TopicSelectTypeCardScreen(
        modifier = modifier,
        topicExpiredDuration = topicSelectUiModel.topicExpiredDuration,
        introduce = topicSelectUiModel.introduce,
        buttonEnabled = buttonEnabled,
        onClickConfirm = onClickConfirm,
        isEvent = false
    ) {
        Spacer(modifier = Modifier.weight(1f))
        topicSelectUiModel.topics.take(4).forEach { topic ->
            FourTypeTopic(
                iconUrl = topic.iconUrl,
                iconRes = topic.iconRes,
                title = topic.title,
                content = topic.content,
                key = topic.idx,
                isSelect = selectTopicIdx == topic.idx,
                topicClickListener = onSelectTopic,
                hasSelectTopic = hasSelectTopic
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
@Preview
private fun FourTypeTopicSelectCardPreview() {
    FourTypeTopicSelectCard(
        topicSelectUiModel = TopicSelectUiModel.FourTopic(
            introduce = "안녕하세요",
            topicExpiredDuration = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS),
            topics = dummyTopics
        ),
        selectTopicIdx = 0,
        buttonEnabled = true
    )
}

@Composable
@Preview
private fun FourTypeTopicSelectCardNoneSelectPreview() {
    FourTypeTopicSelectCard(
        topicSelectUiModel = TopicSelectUiModel.FourTopic(
            introduce = "안녕하세요",
            topicExpiredDuration = System.currentTimeMillis().toDuration(DurationUnit.MILLISECONDS),
            topics = dummyTopics
        ),
        selectTopicIdx = -1,
        buttonEnabled = true
    )
}
