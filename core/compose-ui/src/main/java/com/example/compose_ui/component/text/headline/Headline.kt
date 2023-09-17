package com.example.compose_ui.component.text.headline

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import com.example.compose_ui.component.font.pretendardFontStyle
import com.example.compose_ui.extensions.dpTextUnit


@Composable
fun ThtHeadline1(
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
            fontSize = 30.dpTextUnit
        ),
        color = color,
    )
}

@Composable
fun ThtHeadline2(
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
        style = pretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 26.dpTextUnit,
            shadow = shadow
        ),
        color = color,
    )
}

@Composable
fun ThtHeadline3(
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
            fontSize = 24.dpTextUnit
        ),
        color = color,
    )
}

@Composable
fun ThtHeadline4(
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
        style = pretendardFontStyle(
            fontWeight = fontWeight,
            fontSize = 19.dpTextUnit,
            shadow = shadow
        ),
        color = color,
    )
}

@Composable
fun ThtHeadline5(
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
            fontSize = 17.dpTextUnit,
        ),
        color = color,
    )
}
