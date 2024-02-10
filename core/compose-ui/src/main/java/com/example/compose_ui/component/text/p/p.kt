package com.example.compose_ui.component.text.p

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.compose_ui.component.font.rememberPretendardFontStyle
import com.example.compose_ui.extensions.dpTextUnit

@Composable
fun ThtP1(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
    shadow: Shadow? = null
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = rememberPretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 14.dpTextUnit,
            shadow = shadow
        ),
        color = color,
    )
}

@Composable
fun ThtP2(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
    includeFontPadding: Boolean = false
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = rememberPretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 12.dpTextUnit,
            includeFontPadding = includeFontPadding
        ),
        color = color,
    )
}
