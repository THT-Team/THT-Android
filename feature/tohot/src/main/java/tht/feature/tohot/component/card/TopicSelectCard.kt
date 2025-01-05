package tht.feature.tohot.component.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import tht.feature.tohot.model.TopicSelectUiModel

@Composable
fun TopicSelectCard(
    topicCard: TopicSelectUiModel,
    selectTopicIdx: Int,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { }
) {
    when (topicCard) {
        is TopicSelectUiModel.OneTopic -> {
            OneTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                isSelect = topicCard.topic.idx == selectTopicIdx,
                buttonEnabled = topicCard.topic.idx == selectTopicIdx,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
        is TopicSelectUiModel.TwoTopic -> {
            val enable = topicCard.topic1.idx == selectTopicIdx || topicCard.topic2.idx == selectTopicIdx
            TwoTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                selectTopicIdx = selectTopicIdx,
                buttonEnabled = enable,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
        is TopicSelectUiModel.FourTopic -> {
            val enable = remember(topicCard, selectTopicIdx) {
                topicCard.topics.any { it.idx == selectTopicIdx }
            }
            FourTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                selectTopicIdx = selectTopicIdx,
                buttonEnabled = enable,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
    }
}
