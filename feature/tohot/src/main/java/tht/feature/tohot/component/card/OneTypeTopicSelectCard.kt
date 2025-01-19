package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.noRippleClickable
import tht.core.ui.R
import tht.feature.tohot.component.topic.TopicItemChipImage
import tht.feature.tohot.model.TopicSelectUiModel
import tht.feature.tohot.model.TopicUiModel
import tht.feature.tohot.model.dummyTopics
import kotlin.time.Duration

@Composable
fun OneTypeTopicSelectCard(
    topicSelectUiModel: TopicSelectUiModel.OneTopic,
    isSelect: Boolean,
    buttonEnabled: Boolean,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { },
    onClickConfirm: () -> Unit = { }
) {
    TopicSelectTypeCardScreen(
        modifier = modifier,
        topicExpiredDuration = topicSelectUiModel.topicExpiredDuration,
        introduce = topicSelectUiModel.introduce,
        buttonEnabled = buttonEnabled,
        onClickConfirm = onClickConfirm,
        isEvent = topicSelectUiModel.isEvent
    ) {
        OneTypeTopic(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            topicUiModel = topicSelectUiModel.topic,
            isSelect = isSelect,
            onSelectTopic = onSelectTopic
        )
    }
}

@Composable
private fun OneTypeTopic(
    topicUiModel: TopicUiModel,
    isSelect: Boolean,
    modifier: Modifier = Modifier,
    onSelectTopic: (Int) -> Unit = { }
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
            .noRippleClickable { onSelectTopic(topicUiModel.idx) },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
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
            text = topicUiModel.title,
            fontWeight = FontWeight.Normal,
            color = colorResource(R.color.gray_8d8d8d)
        )
        ThtHeadline5(
            text = topicUiModel.content,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(R.color.white_f9fafa)
        )
    }
}

@Composable
@Preview
private fun OneTypeTopicSelectCardPreview() {
    OneTypeTopicSelectCard(
        topicSelectUiModel = TopicSelectUiModel.OneTopic(
            topic = dummyTopics.first(),
            isEvent = true,
            introduce = "introduce",
            topicExpiredDuration = Duration.ZERO
        ),
        buttonEnabled = true,
        isSelect = true
    )
}
