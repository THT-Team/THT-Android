package tht.feature.tohot.component.blur

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.compose_ui.extensions.onDoubleTab
import tht.feature.tohot.R

@Composable
fun Android11ContentBlur(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    blurOn: Boolean,
    clickableBlurContent: Boolean,
    onDoubleTab: () -> Unit = {},
//    blurBitmap: Bitmap?,
    content: @Composable () -> Unit
) {
    if (!blurOn) {
        content()
        return
    }
    Box(
        modifier = modifier
            .onDoubleTab(
                interceptTouchEvent = !clickableBlurContent,
                onDoubleTab = onDoubleTab
            )
    ) {
//        content()
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
                .graphicsLayer(
                    alpha = 0.5f, // 반투명한 배경 효과
                )
                .background(Color.Black)
        ) {
            content()
//            Image(
//                modifier = Modifier
//                    .fillMaxSize(),
//                bitmap = Toolkit.blur(blurBitmap, 10).asImageBitmap(),
//                contentDescription = "blur content",
//                contentScale = ContentScale.Crop
//            )
        }
    }
}

@Composable
@Preview(apiLevel = 30)
private fun Android11ContentBlurPreview() {
    Android11ContentBlur(
        modifier = Modifier.fillMaxSize(),
        blurOn = true,
        clickableBlurContent = false,
//        blurBitmap = ImageBitmap.imageResource(R.drawable.ic_user_card_placeholder).asAndroidBitmap()
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(id = R.drawable.ic_user_card_placeholder),
            contentScale = ContentScale.FillHeight,
            contentDescription = "Android12BlurContainerPreview"
        )
    }
}
