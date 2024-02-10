package com.example.compose_ui.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import com.example.compose_ui.component.font.rememberPretendardFontStyle

@Composable
fun ThtText(
    text: String,
    fontWeight: FontWeight,
    textSize: TextUnit,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = text,
        style = rememberPretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = textSize,
            lineHeight = lineHeight
        ),
        color = color,
    )
}

@Composable
fun ThtText(
    annotatedString: AnnotatedString,
    fontWeight: FontWeight,
    textSize: TextUnit,
    color: Color,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Center,
    lineHeight: TextUnit = TextUnit.Unspecified,
    includeFontPadding: Boolean = false
) {
    Text(
        modifier = modifier,
        textAlign = textAlign,
        text = annotatedString,
        style = rememberPretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = textSize,
            lineHeight = lineHeight,
            includeFontPadding = includeFontPadding
        ),
        color = color,
    )
}
