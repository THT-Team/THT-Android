package tht.feature.tohot.component.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToHotProgressTimeBackground(
    modifier: Modifier = Modifier,
    color: Color,
    content: @Composable RowScope.() -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(32.dp))
            .background(color = color)
            .padding(horizontal = 10.dp, vertical = 4.dp)
    ) {
        content()
    }
}

@Composable
@Preview
fun ToHotProgressTimeBackgroundPreview() {
    ToHotProgressTimeBackground(
        color = colorResource(id = tht.core.ui.R.color.black_1A1A1A).copy(alpha = 0.5f),
        content = { }
    )
}
