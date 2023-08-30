package tht.feature.setting.component

import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.p.ThtP2

@Composable
fun SettingButton(
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(50),
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    Button(
        modifier = Modifier
            .height(30.dp),
        shape = shape,
        colors = colors,
        onClick = onClick,
        enabled = enabled
    ) {
        content()
    }
}

@Composable
@Preview
fun SettingButtonPreview() {
    SettingButton(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(0xFF222222),
            contentColor = Color(0xFFF9FAFA),
        ),
        onClick = { /*TODO*/ }
    ) {
        ThtP2(textAlign = TextAlign.Center, text = "설정 관리", fontWeight = FontWeight.Normal, color = Color(0xFFF9FAFA))
    }
}
