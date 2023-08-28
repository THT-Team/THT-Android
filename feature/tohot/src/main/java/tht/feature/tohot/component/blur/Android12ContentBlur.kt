package tht.feature.tohot.component.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.compose_ui.extensions.onDoubleTab
import tht.feature.tohot.R

/**
 * https://mashup-android.vercel.app/mashup-12th/hyunkuk/jetpackcompose-blur/
 */
@Composable
fun Android12ContentBlur(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    blurOn: Boolean,
    clickableBlurContent: Boolean,
    onDoubleTab: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (!blurOn) {
        Box(
            modifier = modifier
                .onDoubleTab(
                    interceptTouchEvent = !clickableBlurContent,
                    onDoubleTab = onDoubleTab
                )
        ) {
            content()
        }
        return
    }
    Box(
        modifier = modifier
            .onDoubleTab(
                interceptTouchEvent = !clickableBlurContent,
                onDoubleTab = onDoubleTab
            )
    ) {
        content()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithContent {
                    clipRect(
                        top = padding
                            .calculateTopPadding()
                            .toPx(),
                        left = padding
                            .calculateStartPadding(LayoutDirection.Ltr)
                            .toPx(),
                        right = size.width - padding
                            .calculateEndPadding(LayoutDirection.Ltr)
                            .toPx(),
                        bottom = size.height - padding
                            .calculateBottomPadding()
                            .toPx(),
                    ) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 25.dp, radiusY = 25.dp
                )
        ) {
            content()
        }
    }
}

@Composable
@Preview(apiLevel = 32)
private fun Android12ContentBlurPreview() {
    Android12ContentBlur(
        modifier = Modifier.fillMaxSize(),
        blurOn = true,
        clickableBlurContent = false
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_user_card_placeholder),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Android12BlurContainerPreview"
        )
    }
}
