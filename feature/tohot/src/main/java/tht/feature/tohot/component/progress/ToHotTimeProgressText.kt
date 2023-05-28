package tht.feature.tohot.component.progress

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.compose_ui.component.text.caption.ThtCaption1

@Composable
fun ToHotTimeProgressText(
    modifier: Modifier = Modifier,
    sec: Int,
    textColor: Color
) {
    ThtCaption1(
        modifier = modifier,
        text = sec.toString(),
        fontWeight = FontWeight.Medium,
        color = textColor
    )
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFF000000)
private fun ToHotTimeProgressTextPreview() {
    ToHotTimeProgressText(
        sec = 5,
        textColor = Color.Blue
    )
}
