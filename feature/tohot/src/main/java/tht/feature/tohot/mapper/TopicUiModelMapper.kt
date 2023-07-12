package tht.feature.tohot.mapper

import com.tht.tht.domain.topic.DailyTopicModel
import tht.feature.tohot.model.TopicUiModel


fun DailyTopicModel.toUiModel(): TopicUiModel {
    return TopicUiModel(
        iconUrl = imageUrl,
        iconRes = 0, //TODO: 매핑 필요, 네이밍 고려 필요
        key = key,
        title = title,
        content = content
    )
}
