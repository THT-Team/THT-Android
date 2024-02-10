package com.example.compose_ui.component.progress

import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ThtCircularProgress(
    modifier: Modifier = Modifier,
    visible: Boolean,
    color: Color
) {
    if (visible) {
        CircularProgressIndicator(
            modifier = modifier,
            color = color,
            backgroundColor = color.copy(alpha = 0.1f),
            strokeWidth = 2.dp
        )
    }
}
