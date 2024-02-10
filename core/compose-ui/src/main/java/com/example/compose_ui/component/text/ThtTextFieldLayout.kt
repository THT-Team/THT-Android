package com.example.compose_ui.component.text

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ThtTextFieldLayout(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    underLineColor: Color,
    modifier: Modifier = Modifier,
    singleLine: Boolean = false,
    onClear: (() -> Unit)? = null,
    cursorBrush: Brush = SolidColor(Color.White),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textColor: ThtTextFieldColor = ThtTextFieldColor.default(),
    textStyle: TextStyle = LocalTextStyle.current,
    placeholderTextStyle: TextStyle = LocalTextStyle.current,
    focusRequester: FocusRequester = FocusRequester(),
    errorVisible: Boolean = false,
    error: @Composable (() -> Unit)? = null
) {
    Column {
        ThtTextField(
            onClear = onClear,
            modifier = modifier.focusRequester(focusRequester).weight(1f),
            cursorBrush = cursorBrush,
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder,
            singleLine = singleLine,
            placeholderTextStyle = placeholderTextStyle.copy(
                color = textColor.placeHolder,
                fontWeight = FontWeight.SemiBold
            ),
            textStyle = textStyle.copy(
                color = textColor.text,
                fontWeight = FontWeight.SemiBold
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        Spacer(modifier = Modifier.height(4.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = underLineColor,
            thickness = 2.dp
        )
        AnimatedVisibility(
            visible = error != null && errorVisible
        ) {
            error?.invoke()
        }
    }
}
