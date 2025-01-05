package tht.feature.tohot.mapper

import com.tht.tht.domain.tohot.DailyTopicListModel
import com.tht.tht.domain.tohot.DailyTopicModel
import kotlinx.collections.immutable.toImmutableList
import tht.feature.tohot.R
import tht.feature.tohot.model.TopicSelectUiModel
import tht.feature.tohot.model.TopicUiModel
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun DailyTopicModel.toUiModel(): TopicUiModel {
    return TopicUiModel(
        iconUrl = iconUrl,
        iconRes = R.drawable.ic_topic_item_fun_48, //TODO: 매핑 필요, 네이밍 고려 필요
        idx = idx,
        title = title,
        content = content
    )
}

fun DailyTopicListModel.toUiModel(): TopicSelectUiModel {
    val topicExpiredDuration = (topicResetTimeMill - System.currentTimeMillis()).toDuration(DurationUnit.MILLISECONDS)
    return when(topicSelectType) {
        DailyTopicListModel.TopicSelectType.ONE_CHOICE -> {
            if (topics.isEmpty()) throw Exception("TopicSizeException")
            TopicSelectUiModel.OneTopic(
                isEvent = true,
                topic = topics.first().toUiModel(),
                introduce = introduction,
                topicExpiredDuration = topicExpiredDuration
            )
        }
        DailyTopicListModel.TopicSelectType.TWO_CHOICE -> {
            if (topics.size < 2) throw Exception("TopicSizeException")
            TopicSelectUiModel.TwoTopic(
                topic1 = topics.first().toUiModel(),
                topic2 = topics[1].toUiModel(),
                introduce = introduction,
                topicExpiredDuration = topicExpiredDuration
            )
        }
        DailyTopicListModel.TopicSelectType.FOUR_CHOICE -> {
            if (topics.size < 4) throw Exception("TopicSizeException")
            TopicSelectUiModel.FourTopic(
                topics = topics.take(4).map { it.toUiModel() }.toImmutableList(),
                introduce = introduction,
                topicExpiredDuration = topicExpiredDuration
            )
        }
    }
}
