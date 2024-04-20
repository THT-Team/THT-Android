package tht.feature.setting.composable.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import tht.core.ui.R

@Composable
internal fun MyPageItemInfoRow(
    title: String,
    info: @Composable RowScope.() -> Unit,
    modifier: Modifier = Modifier,
    titleMinWidth: Dp? = null,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .then(
                if (onClick != null) {
                    Modifier.clickable(
                        enabled = true,
                        onClick = onClick
                    )
                } else {
                    Modifier
                }
            )
            .background(
                color = colorResource(id = R.color.black_222222),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 16.dp, vertical = 14.dp)
    ) {
        Row(
            Modifier.widthIn(min = titleMinWidth ?: 89.dp)
        ) {
            ThtSubtitle1(
                textAlign = TextAlign.Start,
                text = title,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.white_f9fafa)
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        Spacer(modifier = Modifier.weight(1f))
        info()
    }
}

@Composable
@Preview
private fun MyPageItemInfoRowPreview() {
    MyPageItemInfoRow(
        title = "title",
        info = {
            ThtSubtitle1(
                text = "info",
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.yellow_f9cc2e)
            )
        },
        onClick = {}
    )
}
