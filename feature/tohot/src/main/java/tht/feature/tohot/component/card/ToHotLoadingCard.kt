package tht.feature.tohot.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCancellationBehavior
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import tht.feature.tohot.R

@Composable
fun ToHotLoadingCard(
    modifier: Modifier = Modifier,
    message: String,
    isVisible: () -> Boolean = { false }
) {
    if (!isVisible()) return
    val loadingComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading)
    )
    val loadingLottieAnimatable = rememberLottieAnimatable()

    LaunchedEffect(key1 = loadingComposition) {
        loadingLottieAnimatable.animate(
            composition = loadingComposition,
            iterations = LottieConstants.IterateForever,
            initialProgress = 0f,
            cancellationBehavior = LottieCancellationBehavior.OnIterationFinish
        )
    }
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
            LottieAnimation(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(100.dp, 50.dp),
                composition = loadingComposition,
                progress = { loadingLottieAnimatable.progress },
                contentScale = ContentScale.FillWidth,
                alignment = Alignment.Center
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
private fun ToHotLoadingCardPreview() {
    ToHotLoadingCard(
        message = stringResource(id = R.string.to_hot_user_card_loading),
        isVisible = { true }
    )
}
