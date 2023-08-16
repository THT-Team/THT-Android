package tht.feature.tohot.component.blur

import android.os.Build
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BlurContainer(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    blurOn: Boolean,
    clickableBlurContent: Boolean,
    onDoubleTab: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
        Android12BlurContainer(
            modifier = modifier,
            padding = padding,
            blurOn = blurOn,
            clickableBlurContent = clickableBlurContent,
            onDoubleTab = onDoubleTab,
            content = content
        )
    } else {

    }
}

@Composable
@Preview
private fun BlurContainerPreview() {

}
