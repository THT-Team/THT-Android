package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import tht.feature.tohot.R

@Composable
fun ToHotLoadingCard(
    modifier: Modifier = Modifier,
    message: String,
    isVisible: () -> Boolean = { false }
) {
    if (!isVisible()) return
    Box(
        modifier = modifier
            .background(
                colorResource(id = tht.core.ui.R.color.black_161616).copy(alpha = 0.5f)
            )
            .fillMaxSize()
            .clip(RoundedCornerShape(12.dp))
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            ThtCircularProgress(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                dataLoading = isVisible
            )
            ThtSubtitle1(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 36.dp),
                text = message,
                fontWeight = FontWeight.Normal,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )
        }
    }
}

@Composable
@Preview
fun ToHotLoadingCardPreview() {
    ToHotLoadingCard(
        message = stringResource(id = R.string.to_hot_user_card_loading),
        isVisible = { true }
    )
}
