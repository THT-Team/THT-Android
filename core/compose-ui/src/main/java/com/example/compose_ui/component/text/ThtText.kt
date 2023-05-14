package com.example.compose_ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign

@Composable
fun ThtText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    text: String,
    style: TextStyle,
    color: Color,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = style,
        color = color,
    )
}
