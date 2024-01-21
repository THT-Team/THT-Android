package com.example.compose_ui.component.toolbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.R

@Composable
fun ThtToolbar(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    onBackPressed: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.height(56.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (onBackPressed != null) {
            IconButton(
                onClick = onBackPressed
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_left_arrow),
                    contentDescription = "ic_left_arrow",
                    tint = colorResource(
                        id = tht.core.ui.R.color.white_f9fafa
                    )
                )
            }
        }
        Spacer(modifier = Modifier.width(16.dp))
        content()
    }
}

@Composable
@Preview
private fun ThtToolbarPreview() {
    ThtToolbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        content = {
            Text(
                text = "Toolbar",
                color = colorResource(
                    id = tht.core.ui.R.color.white_f9fafa
                )
            )
        }
    )
}

@Composable
@Preview
private fun ThtToolbarBackButtonPreview() {
    ThtToolbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        onBackPressed = { },
        content = {
            Text(
                text = "Toolbar",
                color = colorResource(
                    id = tht.core.ui.R.color.white_f9fafa
                )
            )
        }
    )
}
