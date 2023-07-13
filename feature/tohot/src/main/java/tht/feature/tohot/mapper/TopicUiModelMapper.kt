package tht.feature.tohot.mapper

import com.tht.tht.domain.topic.DailyTopicModel
import tht.feature.tohot.R
import tht.feature.tohot.model.TopicUiModel


fun DailyTopicModel.toUiModel(): TopicUiModel {
    return TopicUiModel(
        iconUrl = iconUrl,
        iconRes = R.drawable.ic_topic_item_fun_48, //TODO: 매핑 필요, 네이밍 고려 필요
        key = key,
        title = title,
        content = content
    )
}
