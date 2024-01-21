package com.example.compose_ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.compose_ui.component.font.pretendardFontStyle

@Composable
fun ThtText(
    modifier: Modifier = Modifier,
    textAlign: TextAlign,
    text: String,
    fontWeight: FontWeight,
    textSize: TextUnit,
    color: Color,
) {
    Text(
        modifier = modifier,
        text = text,
        textAlign = textAlign,
        style = pretendardFontStyle(
            fontWeight = fontWeight,
            fontSize =textSize
        ),
        color = color,
    )
}
