package com.example.compose_ui.component.text.caption

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.compose_ui.component.font.pretendardFontStyle
import com.example.compose_ui.extensions.dpTextUnit

@Composable
fun ThtCaption1(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = pretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 11.dpTextUnit
        ),
        color = color,
    )
}

@Composable
fun ThtCaption2(
    modifier: Modifier = Modifier,
    text: String,
    fontWeight: FontWeight,
    color: Color,
    textAlign: TextAlign = TextAlign.Center,
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = pretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 10.dpTextUnit
        ),
        color = color,
    )
}