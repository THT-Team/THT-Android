package tht.feature.tohot.component.card

import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.extensions.onDoubleTab
import tht.feature.tohot.R
import tht.feature.tohot.component.hold.BlurContent
import tht.feature.tohot.component.hold.ToHotHoldUi

@Composable
fun ToHotHoldCard(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    isHold: Boolean,
    clickableContent: Boolean,
    onDoubleTab: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (!isHold) {
        Box(
            modifier = modifier
                .onDoubleTab(
                    interceptTouchEvent = !clickableContent,
                    onDoubleTab = onDoubleTab
                )
        ) {
            content()
        }
        return
    }
    Box(
        modifier = Modifier
            .onDoubleTab(
                interceptTouchEvent = !clickableContent,
                onDoubleTab = onDoubleTab
            )
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S_V2) {
            BlurContent(
                padding = padding,
                content = content
            )
        }
        ToHotHoldUi()
    }
}

@Composable
@Preview
private fun ToHotHoldCardPreview() {
    ToHotHoldCard(
        modifier = Modifier.fillMaxSize(),
        isHold = true,
        clickableContent = false,
        onDoubleTab = {}
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_user_card_placeholder),
            contentDescription = "ic_user_card_placeholder"
        )
    }
}
