package tht.feature.signin.signup.signupcomplete.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.size.Size
import com.example.compose_ui.component.card.CardWithAnimatedBorder
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.ThtText
import tht.core.ui.R
import tht.core.ui.extension.showToast
import tht.feature.signin.ui.SignupLargeButton

@Composable
internal fun SignupCompleteScreen(
    loading: Boolean,
    profileImage: String?,
    onComplete: () -> Unit,
    modifier: Modifier = Modifier,
    error: Throwable? = null
) {
    val context = LocalContext.current
    Box {
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.black_161616))
                .padding(horizontal = 22.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    ThtText(
                        text = "환영해요",
                        textSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        lineHeight = 33.6.sp,
                        color = colorResource(R.color.white_f9fafa)
                    )
                    Icon(
                        modifier = Modifier.size(34.dp),
                        painter = painterResource(
                            tht.feature.signin.R.drawable.ic_topic
                        ),
                        tint = colorResource(R.color.yellow_f9cc2e),
                        contentDescription = "ic_topic"
                    )
                }
                ThtText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "모든 준비가 끝났어요",
                    textSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 33.6.sp,
                    color = colorResource(R.color.white_f9fafa)
                )
                Spacer(modifier = Modifier.height(16.dp))
                ThtText(
                    modifier = Modifier.fillMaxWidth(),
                    text = "폴링에 한번 빠져보시겠어요?",
                    textSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    lineHeight = 22.1.sp,
                    color = colorResource(R.color.gray_8d8d8d)
                )
            }
            CardWithAnimatedBorder(
                modifier = Modifier
                    .size(182.dp)
                    .align(Alignment.CenterHorizontally),
                borderColors = listOf(Color(0xFFF9CC2E), Color(0xFFFF7539))
            ) {
                Box(
                    modifier = Modifier
                        .background(color = colorResource(R.color.black_161616))
                        .padding(11.dp)
                ) {
                    val model = remember(profileImage) {
                        ImageRequest.Builder(context)
                            .data(profileImage)
                            .size(Size.ORIGINAL)
                            .crossfade(true)
                            .build()
                    }
                    AsyncImage(
                        modifier = modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(20.dp)),
                        model = model,
                        contentDescription = "profile_image",
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            SignupLargeButton(
                onClick = onComplete,
                text = stringResource(id = tht.feature.signin.R.string.yes),
                enable = !loading
            )
            Spacer(modifier = Modifier.height(22.dp))
        }
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_f9cc2e),
            visible = loading
        )
    }
    LaunchedEffect(error) {
        if (error != null) {
            context.showToast(error.message ?: error.toString())
        }
    }
}
