package tht.feature.tohot.component.card

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import tht.feature.tohot.model.TopicSelectUiModel

@Composable
fun TopicSelectCard(
    topicCard: TopicSelectUiModel,
    selectTopicKey: Int,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { }
) {
    when (topicCard) {
        is TopicSelectUiModel.OneTopic -> {
            OneTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                isSelect = topicCard.topic.key == selectTopicKey,
                buttonEnabled = topicCard.topic.key == selectTopicKey,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
        is TopicSelectUiModel.TwoTopic -> {
            val enable = topicCard.topic1.key == selectTopicKey || topicCard.topic2.key == selectTopicKey
            TwoTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                selectTopicKey = selectTopicKey,
                buttonEnabled = enable,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
        is TopicSelectUiModel.FourTopic -> {
            val enable = remember(topicCard, selectTopicKey) {
                topicCard.topics.any { it.key == selectTopicKey }
            }
            FourTypeTopicSelectCard(
                topicSelectUiModel = topicCard,
                selectTopicKey = selectTopicKey,
                buttonEnabled = enable,
                modifier = modifier,
                onSelectTopic = onSelectTopic,
                onClickConfirm = onClickConfirm
            )
        }
    }
}
