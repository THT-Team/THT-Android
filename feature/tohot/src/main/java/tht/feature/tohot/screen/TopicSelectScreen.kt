package tht.feature.tohot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.feature.tohot.R
import tht.feature.tohot.component.topic.TopicSelectChip
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.TopicUiModel
import tht.feature.tohot.model.topics

@Composable
fun TopicSelectScreen(
    modifier: Modifier = Modifier,
    remainingTime: String,
    topics: ImmutableListWrapper<TopicUiModel>,
    selectTopicKey: Long,
    buttonEnabled: Boolean,
    topicClickListener: (Long) -> Unit = { },
    selectFinishListener: () -> Unit = { }
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = tht.core.ui.R.color.black_222222)
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 30.dp, top = 22.dp, end = 30.dp)
        ) {
            ThtHeadline4(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.select_topic_title),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa),
                textAlign = TextAlign.Start
            )

            Image(
                painter = painterResource(id = R.drawable.ic_stopwatch_yellow),
                contentDescription = "ic_stopwatch"
            )

            ThtHeadline5(
                modifier = Modifier.padding(start = 8.dp),
                text = remainingTime,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        topics.list.forEach {
            TopicSelectChip(
                modifier = Modifier
                    .padding(horizontal = 22.dp),
                imageUrl = it.imageUrl,
                imageRes = it.imageRes,
                title = it.title,
                content = it.content,
                key = it.key,
                isSelect = selectTopicKey == it.key,
                topicClickListener = topicClickListener
            )

            Spacer(modifier = Modifier.height(14.dp))
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 22.dp)
                .heightIn(min = 54.dp)
                .clip(RoundedCornerShape(12.dp)),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                contentColor = colorResource(id = tht.core.ui.R.color.black_222222)
            ),
            enabled = buttonEnabled,
            onClick = selectFinishListener
        ) {
            ThtHeadline5(
                text = stringResource(id = R.string.starting),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.black_222222)
            )
        }
    }
}

@Composable
@Preview
fun TopicSelectScreenPreview() {
    TopicSelectScreen(
        remainingTime = "24:00:00",
        topics = ImmutableListWrapper(topics),
        selectTopicKey = 1L,
        buttonEnabled = true
    )
}
