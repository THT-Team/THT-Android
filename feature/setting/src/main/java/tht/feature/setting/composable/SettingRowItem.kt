package tht.feature.setting.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1

@Composable
fun SettingRowItem(
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                color = colorResource(id = tht.core.ui.R.color.black_222222),
                shape = RoundedCornerShape(10.dp)
            )
            .padding(horizontal = 14.dp, vertical = 15.dp)
            .clickable(
                enabled = true,
                onClick = onClick
            )
    ) {
        ThtSubtitle1(
            text = title,
            fontWeight = FontWeight.Normal,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(id = com.example.compose_ui.R.drawable.ic_right_arrow),
            contentDescription = "ic_right_arrow",
            tint = colorResource(id = tht.core.ui.R.color.white_f9fafa)
        )
    }
}

@Composable
@Preview
private fun SettingRowItemPreview() {
    SettingRowItem(
        title = "title",
        onClick = {}
    )
}
