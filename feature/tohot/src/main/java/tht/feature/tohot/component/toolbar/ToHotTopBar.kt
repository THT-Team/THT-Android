package tht.feature.tohot.component.toolbar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ToHotTopAppBar(
    rightIcons: @Composable () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .padding(
                horizontal = 20.dp,
                vertical = (15.5).dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        rightIcons()
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
internal fun ToHotTopAppBarPreview() {
    ToHotTopAppBar { }
}
