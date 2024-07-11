package tht.feature.signin.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.core.ui.R

@Composable
internal fun SignupLargeButton(
    text: String,
    enable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(
        backgroundColor = colorResource(id = R.color.yellow_f9cc2e),
        contentColor = Color.Transparent,
        disabledBackgroundColor = colorResource(id = R.color.brown_26241f),
        disabledContentColor = Color.Transparent
    ),
    textColor: Color = colorResource(id = R.color.black_222222)
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        enabled = enable,
        onClick = onClick,
        colors = colors,
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        ThtHeadline5(
            text = text,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}

@Composable
@Preview
private fun SignupLargeButtonPreview() {
    SignupLargeButton(
        modifier = Modifier.imePadding(),
        enable = true,
        onClick = {},
        text = "Button"
    )
}

@Composable
@Preview
private fun SignupLargeButtonPreview2() {
    SignupLargeButton(
        modifier = Modifier.imePadding(),
        enable = false,
        onClick = {},
        text = "Button"
    )
}
