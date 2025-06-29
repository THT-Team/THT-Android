package com.example.compose_ui.component.dialog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.compose_ui.extensions.dpTextUnit

@Composable
fun ThtDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    cancellable: Boolean = true,
    title: (@Composable () -> Unit)? = null,
    description: (@Composable () -> Unit)? = null,
    content: (@Composable () -> Unit)? = null,
    buttonBuilder: (@Composable () -> Unit)? = null,
) {
    val properties = remember(cancellable) {
        DialogProperties(
            dismissOnBackPress = cancellable,
            dismissOnClickOutside = cancellable,
            usePlatformDefaultWidth = false,
        )
    }

    Dialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
    ) {
        ThtDialogLayout(
            modifier = modifier,
            title = title,
            description = description,
            content = content,
            buttons = buttonBuilder,
        )
    }
}

@Composable
fun ThtDialogLayout(
    modifier: Modifier = Modifier,
    title: @Composable (() -> Unit)? = null,
    description: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    buttons: @Composable (() -> Unit)? = null,
) {
    Surface(
        modifier = modifier
            .padding(horizontal = 40.dp)
            .widthIn(max = 480.dp)
            .fillMaxWidth(),
        color = Color(0xFF222222),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if (title != null) {
                Spacer(modifier = Modifier.height(16.dp))
                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 16.dpTextUnit,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9FAFA),
                        textAlign = TextAlign.Center,
                    ),
                ) {
                    title()
                }
            }

            if (description != null) {
                Spacer(modifier = Modifier.height(8.dp))

                CompositionLocalProvider(
                    LocalTextStyle provides TextStyle(
                        fontSize = 14.sp,
                        color = Color(0xFF8D8D8D),
                        textAlign = TextAlign.Center,
                    ),
                ) {
                    description()
                }
            }

            if (content != null) {
                Spacer(modifier = Modifier.height(24.dp))
                content()
            }

            if (buttons != null) {
                buttons()
            }
        }
    }
}
