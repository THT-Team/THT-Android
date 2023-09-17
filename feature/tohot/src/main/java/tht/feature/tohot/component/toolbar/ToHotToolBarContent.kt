package tht.feature.tohot.component.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.tohot.R
import tht.feature.tohot.component.topic.TopicItemChipImage

@Composable
fun RowScope.ToHotToolBarContent(
    topicIconUrl: String?,
    topicIconRes: Int?,
    topicIconSize: Dp = 38.dp,
    topicTitle: String?,
    hasUnReadAlarm: Boolean,
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { }
) {
    Row(
        modifier = Modifier
            .noRippleClickable(topicSelectListener),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (topicIconRes != null && !topicTitle.isNullOrBlank()) {
            TopicItemChipImage(
                modifier = Modifier.size(topicIconSize),
                imageUrl = topicIconUrl,
                placeholder = painterResource(id = R.drawable.ic_topic_placeholder_38),
                error = painterResource(id = topicIconRes)
            )
        } else {
            Image(
                modifier = Modifier.size(topicIconSize),
                painter = painterResource(id = R.drawable.ic_topic_default),
                contentDescription = "topic_default_item"
            )
        }

        ThtHeadline4(
            modifier = Modifier.padding(start = 12.dp),
            text = topicTitle ?: stringResource(id = R.string.falling),
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
        )
    }

    Spacer(modifier = Modifier.weight(1f))

    val alarmRes = if (hasUnReadAlarm) R.drawable.ic_alarm_on else R.drawable.ic_alarm_default
    Image(
        modifier = Modifier
            .noRippleClickable(alarmClickListener)
            .size(38.dp),
        painter = painterResource(id = alarmRes),
        contentDescription = "alarm"
    )
}

@Composable
@Preview
private fun DefaultTopicToHotToolBarContentPreview() {
    ToHotToolBar {
        ToHotToolBarContent(
            topicIconUrl = null,
            topicIconRes = null,
            topicTitle = null,
            hasUnReadAlarm = true
        )
    }
}

@Composable
@Preview
private fun ToHotToolBarContentPreview() {
    ToHotToolBar {
        ToHotToolBarContent(
            topicIconUrl = "123",
            topicIconRes = R.drawable.ic_topic_item_values_38,
            topicTitle = "휴식",
            hasUnReadAlarm = false
        )
    }
}

@Composable
@Preview
private fun AlarmOnToHotToolBarContentPreview() {
    ToHotToolBar {
        ToHotToolBarContent(
            topicIconUrl = "123",
            topicIconRes = R.drawable.ic_topic_item_values_38,
            topicTitle = "휴식",
            hasUnReadAlarm = true
        )
    }
}

@Composable
@Preview
private fun NoneTopicToHotToolBarContentPreview() {
    ToHotToolBar {
        ToHotToolBarContent(
            topicIconUrl = null,
            topicIconRes = R.drawable.ic_topic_item_values_38,
            topicTitle = "휴식",
            hasUnReadAlarm = true
        )
    }
}
