package tht.feature.tohot.component.toolbar

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.tohot.R

@Composable
fun RowScope.ToHotToolBarContent(
    topicIconUrl: String?,
    topicIconRes: Int?,
    topicTitle: String?,
    hasUnReadAlarm: Boolean,
    topicSelectListener: () -> Unit = { },
    alarmClickListener: () -> Unit = { }
) {
    if (!topicIconUrl.isNullOrBlank() && topicIconRes != null && !topicTitle.isNullOrBlank()) {
        Row(
            modifier = Modifier
                .noRippleClickable(topicSelectListener),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ToHotToolbarTopicIcon(
                size = DpSize(38.dp, 38.dp),
                topicIconUrl = topicIconUrl,
                topicIconRes = topicIconRes
            )

            ThtHeadline4(
                modifier = Modifier.padding(start = 12.dp),
                text = topicTitle,
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )
        }
    }
    Spacer(modifier = Modifier.weight(1f))

    val alarmRes = if (hasUnReadAlarm) R.drawable.ic_alarm_on else R.drawable.ic_alarm_default
    Image(
        modifier = Modifier.noRippleClickable(alarmClickListener),
        painter = painterResource(id = alarmRes),
        contentDescription = "alarm"
    )
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
            topicIconUrl = "",
            topicIconRes = R.drawable.ic_topic_item_values_38,
            topicTitle = "휴식",
            hasUnReadAlarm = true
        )
    }
}
