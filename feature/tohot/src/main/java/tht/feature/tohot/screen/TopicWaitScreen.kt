package tht.feature.tohot.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP1
import tht.core.ui.R

@Composable
fun TopicWaitScreen(
    modifier: Modifier = Modifier,
    remainingTime: String
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(id = R.color.black_222222)
            )
    ) {
        Row(
            modifier = Modifier.padding(start = 30.dp, top = 22.dp, end = 30.dp)
        ) {
            ThtHeadline4(
                modifier = Modifier.weight(1f),
                text = stringResource(id = tht.feature.tohot.R.string.select_topic_title),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.white_f9fafa),
                textAlign = TextAlign.Start
            )

            Image(
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_stopwatch_yellow),
                contentDescription = "ic_stopwatch_yellow"
            )

            ThtHeadline5(
                modifier = Modifier.padding(start = 8.dp),
                text = remainingTime,
                fontWeight = FontWeight.Medium,
                color = colorResource(id = R.color.yellow_f9cc2e)
            )
        }

        Spacer(modifier = Modifier.height(22.dp))

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = tht.feature.tohot.R.drawable.ic_topic_wait),
                contentDescription = "ic_topic_wait"
            )
        }

        ThtHeadline4(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(id = tht.feature.tohot.R.string.to_hot_topic_wait),
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = R.color.white_f9fafa)
        )

        ThtP1(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            text = stringResource(id = tht.feature.tohot.R.string.to_hot_topic_wait_description),
            fontWeight = FontWeight.Normal,
            color = colorResource(id = R.color.gray_8d8d8d)
        )

        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
@Preview
fun TopicWaitScreenPreview() {
    TopicWaitScreen(
        remainingTime = "24:00:00"
    )
}
