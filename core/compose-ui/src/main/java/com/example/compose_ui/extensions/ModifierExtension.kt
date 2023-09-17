package com.example.compose_ui.extensions

import android.view.MotionEvent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.input.pointer.pointerInteropFilter

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    then(
        clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
        ) {
            onClick()
        }
    )
}

@OptIn(ExperimentalComposeUiApi::class)
fun Modifier.onDoubleTab(
    doubleIntervalMill: Long = 300L,
    interceptTouchEvent: Boolean,
    onDoubleTab: () -> Unit
): Modifier = composed {
    var doubleTabTouchTimeMill by remember { mutableStateOf(0L) }
    then(
        pointerInteropFilter {
            when (it.action) {
                MotionEvent.ACTION_UP -> {
                    System.currentTimeMillis().let { now ->
                        doubleTabTouchTimeMill = if (doubleTabTouchTimeMill != 0L && now - doubleTabTouchTimeMill <= doubleIntervalMill) {
                            onDoubleTab()
                            0L
                        } else {
                            now
                        }
                    }
                }
                else -> {}
            }
            interceptTouchEvent
        }
    )
}
