package com.example.compose_ui.common

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember

class RecompositionCounter(var value: Int)

@Composable
inline fun LogComposition(
    tag: String,
    msg: String = "",
) {
    DisposableEffect(Unit) {
        onDispose {
            Log.d(tag, "Dispose: $msg")
        }
    }
    val recompositionCounter = remember { RecompositionCounter(0) }
    SideEffect { recompositionCounter.value++ }
    Log.d(tag, "Composition: $msg ${recompositionCounter.value}")
}

