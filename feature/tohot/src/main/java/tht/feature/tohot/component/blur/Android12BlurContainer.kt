package tht.feature.tohot.component.blur

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import tht.feature.tohot.R

/**
 * https://mashup-android.vercel.app/mashup-12th/hyunkuk/jetpackcompose-blur/
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Android12BlurContainer(
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(0.dp),
    blurOn: Boolean,
    clickableBlurContent: Boolean,
    onDoubleTab: () -> Unit = {},
    content: @Composable () -> Unit
) {
    if (!blurOn) {
        content()
        return
    }
    var touchTimeMill by remember { mutableStateOf(0L) }
    Box(
        modifier = modifier
            .pointerInteropFilter {
                when (it.action) {
                    MotionEvent.ACTION_UP -> {
                        System.currentTimeMillis().let { now ->
                            touchTimeMill = if (touchTimeMill != 0L && now - touchTimeMill <= 300L) {
                                onDoubleTab()
                                0L
                            } else {
                                now
                            }
                        }
                    }
                }
                !clickableBlurContent
            }
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        Log.d("TAG", "onDoubleTap")
                    }
                )
            }
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
                    radiusX = 10.dp, radiusY = 10.dp
                )
        ) {
            content()
        }
    }
}

@Composable
@Preview(apiLevel = 32)
private fun Android12BlurContainerPreview() {
    Android12BlurContainer(
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
