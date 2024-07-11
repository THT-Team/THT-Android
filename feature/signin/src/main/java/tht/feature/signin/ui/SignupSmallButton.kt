package tht.feature.signin.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import tht.core.ui.R

@Composable
internal fun SignupSmallButton(
    enable: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        modifier = Modifier
            .width(88.dp)
            .height(54.dp)
            .then(modifier),
        shape = RoundedCornerShape(16.dp),
        enabled = enable,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = colorResource(id = R.color.yellow_f9cc2e),
            contentColor = Color.Transparent,
            disabledBackgroundColor = colorResource(id = R.color.brown_26241f),
            disabledContentColor = Color.Transparent
        ),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        Icon(
            painter = painterResource(id = tht.feature.signin.R.drawable.ic_right_arrow_black),
            contentDescription = "ic_right_arrow_black",
            tint = Color.Black
        )
    }
}

@Composable
@Preview
private fun SignupSmallButtonEnablePreview() {
    SignupSmallButton(
        enable = true,
        onClick = {}
    )
}

@Composable
@Preview
private fun SignupSmallButtonPreview() {
    SignupSmallButton(
        enable = false,
        onClick = {}
    )
}
