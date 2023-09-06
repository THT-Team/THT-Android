package tht.feature.tohot.component.hold

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.headline.ThtHeadline4
import com.example.compose_ui.component.text.p.ThtP1
import tht.feature.tohot.R

@Composable
fun ToHotHoldUi(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Spacer(weight = 1f)

        Image(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_pause),
            contentDescription = "ic_pause"
        )
        ThtHeadline4(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 32.dp),
            text = "잠시 쉬었다 가시겠어요?",
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa),
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.34f),
                offset = Offset(1f, 1f),
                blurRadius = 4f
            )
        )

        ThtP1(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp),
            text = "저희가 잠시 홀드해 놓을게요",
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa),
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.34f),
                offset = Offset(1f, 1f),
                blurRadius = 4f
            )
        )

        Spacer(weight = 1f)

        ThtP1(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 20.dp),
            text = "더블탭으로 다시 시작하기",
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa),
            shadow = Shadow(
                color = Color.Black.copy(alpha = 0.34f),
                offset = Offset(1f, 1f),
                blurRadius = 4f
            )
        )
    }
}

@Composable
@Preview
private fun ToHotHoldUiPreview() {
    ToHotHoldUi()
}
