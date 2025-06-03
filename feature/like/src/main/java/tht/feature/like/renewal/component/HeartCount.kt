package tht.feature.like.renewal.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.extensions.dpTextUnit


@Composable
@Preview
internal fun HeartCount(
    count: Int = 999
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(corner = CornerSize(12.dp)))
            .background(Color(0xFF111111))
            .padding(vertical = 10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        fontSize = 16.dpTextUnit,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFFF9CC2E),
                    )
                ) {
                    append("무디 ${count}명")
                }
                withStyle(
                    SpanStyle(
                        fontSize = 16.dpTextUnit,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                ) {
                    append("이 나를 좋게 생각해요 :)")
                }
            }
        )
    }
}
