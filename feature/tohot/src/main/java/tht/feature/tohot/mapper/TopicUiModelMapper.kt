package tht.feature.tohot.mapper

import com.tht.tht.domain.topic.DailyTopicModel
import tht.feature.tohot.model.TopicUiModel

/**
 * TODO: ImageUrl 은 하나, Client 에서 이미지 Size 조절 필요
 */
fun DailyTopicModel.toUiModel(): TopicUiModel {
    return TopicUiModel(
        imageUrl = imageUrl,
        imageRes = 0, //TODO: 매핑 필요, 네이밍 고려 필요
        key = key,
        title = title,
        content = content,
        iconUrl = imageUrl,
        iconRes = 0 //TODO: 매핑 필요, 네이밍 고려 필요
    )
}
