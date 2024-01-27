package com.example.compose_ui.component.font

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import com.example.compose_ui.R

val pretendardFontFamily = FontFamily(
    Font(R.font.pretendard_black, FontWeight.Black),
    Font(R.font.pretendard_bold, FontWeight.Bold),
    Font(R.font.pretendard_extra_bold, FontWeight.ExtraBold),
    Font(R.font.pretendard_extra_light, FontWeight.ExtraLight),
    Font(R.font.pretendard_light, FontWeight.Light),
    Font(R.font.pretendard_medium, FontWeight.Medium),
    Font(R.font.pretendard_regular, FontWeight.Normal),
    Font(R.font.pretendard_semi_bold, FontWeight.SemiBold),
    Font(R.font.pretendard_thin, FontWeight.Thin),
)

@Composable
fun rememberPretendardFontStyle(
    fontWeight: FontWeight,
    fontSize: TextUnit,
    lineHeight: TextUnit = TextUnit.Unspecified,
    color: Color = Color.Unspecified,
    shadow: Shadow? = null
): TextStyle {
    return remember {
        TextStyle(
            color = color,
            fontFamily = pretendardFontFamily,
            fontWeight = fontWeight,
            fontSize = fontSize,
            shadow = shadow,
            lineHeight = lineHeight
        )
    }
}
