package com.example.compose_ui.component.text

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
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
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: ThtTextFieldColor = ThtTextFieldColor.default(),
    textStyle: TextStyle = LocalTextStyle.current,
    placeholderTextStyle: TextStyle = LocalTextStyle.current
) {
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
