package tht.feature.tohot.tohot.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.headline.ThtHeadline2
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.subtitle.ThtSubtitle1
import com.example.compose_ui.component.text.subtitle.ThtSubtitle2
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.tohot.R
import tht.feature.tohot.component.card.ToHotCardImage

/**
 * https://coil-kt.github.io/coil/compose/#observing-asyncimagepainterstate
 * rememberAsyncImagePainter 로 Painter 객체를 얻어 와, ImageView 에 적용 시켜 주면 추가 로딩이 없음
 * 혹은 이미 캐시된 이미지 이용
 */
@Composable
fun ToHotMatchingScreen(
    modifier: Modifier = Modifier,
    imageUrl: String,
    onChatClick: () -> Unit = {},
    onCloseClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
    ) {
        ToHotCardImage(
            modifier = Modifier.fillMaxSize(),
            imageUrl = imageUrl
        )

        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ThtHeadline2(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 142.dp),
                text = stringResource(id = R.string.to_hot_matching_title),
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )
            ThtSubtitle1(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = stringResource(id = R.string.to_hot_matching_content),
                fontWeight = FontWeight.SemiBold,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )

            androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(1f))

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 28.dp, end = 28.dp, top = 32.dp)
                    .clip(RoundedCornerShape(16.dp)),
                onClick = onChatClick,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
                )
            ) {
                ThtHeadline5(
                    modifier = Modifier.padding(vertical = 15.dp),
                    text = stringResource(id = R.string.to_hot_matching_btn),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = tht.core.ui.R.color.black_222222)
                )
            }

            ThtSubtitle2(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 20.dp, start = 20.dp, end = 20.dp)
                    .noRippleClickable { onCloseClick() },
                text = stringResource(id = R.string.close),
                fontWeight = FontWeight.Medium,
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )

            Spacer(space = 80.dp)
        }
    }
}

@Composable
@Preview
private fun ToHotMatchingScreenPreview() {
    ToHotMatchingScreen(
        modifier = Modifier.fillMaxSize(),
        imageUrl = "https://profile"
    )
}
