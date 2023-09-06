package tht.feature.tohot.component.hold

import android.os.Build
import androidx.annotation.RequiresApi
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
import tht.feature.tohot.R

/**
 * https://mashup-android.vercel.app/mashup-12th/hyunkuk/jetpackcompose-blur/
 */
@Composable
@RequiresApi(value = 32)
fun BlurContent(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    content: @Composable () -> Unit
) {
    Box(modifier = modifier) {
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
                            .toPx()
                    ) {
                        this@drawWithContent.drawContent()
                    }
                }
                .blur(
                    radiusX = 25.dp,
                    radiusY = 25.dp
                )
        ) {
            content()
        }
    }
}

@RequiresApi(Build.VERSION_CODES.S_V2)
@Composable
@Preview(apiLevel = 32)
private fun Android12ContentBlurPreview() {
    BlurContent(modifier = Modifier.fillMaxSize()) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_user_card_placeholder),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Android12BlurContainerPreview"
        )
    }
}
