package com.example.compose_ui.component.text

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import tht.core.ui.R

@Composable
fun ThtTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    singleLine: Boolean = false,
    modifier: Modifier = Modifier,
    onClear: (() -> Unit)? = null,
    cursorBrush: Brush = SolidColor(Color.White),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: ThtTextFieldColor = ThtTextFieldColor.default(),
    textStyle: TextStyle = LocalTextStyle.current,
    placeholderTextStyle: TextStyle = LocalTextStyle.current,
) {
    Row {
        Box(
            modifier = modifier
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (value.isEmpty()) 100f else 0f),
                text = placeholder,
                style = placeholderTextStyle.copy(
                    color = textColor.placeHolder,
                    fontWeight = FontWeight.SemiBold
                )
            )
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .alpha(if (value.isEmpty()) 0f else 100f),
                value = value,
                cursorBrush = cursorBrush,
                onValueChange = onValueChange,
                textStyle = textStyle.copy(
                    color = textColor.text,
                    fontWeight = FontWeight.SemiBold
                ),
                singleLine = singleLine,
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions
            )
        }

        if (onClear != null && value.isNotEmpty()) {
            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .clickable(true, onClick = onClear),
                painter = painterResource(id = com.example.compose_ui.R.drawable.ic_delete),
                contentDescription = "ic_delete"
            )
        }
    }
}

@Immutable
data class ThtTextFieldColor(
    val placeHolder: Color,
    val text: Color
) {
    companion object {
        @Composable
        fun default(): ThtTextFieldColor {
            return ThtTextFieldColor(
                placeHolder = colorResource(id = R.color.gray_8d8d8d),
                text = colorResource(id = R.color.yellow_f9cc2e),
            )
        }
    }
}
