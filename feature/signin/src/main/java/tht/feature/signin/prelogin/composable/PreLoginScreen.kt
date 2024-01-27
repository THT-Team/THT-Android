package tht.feature.signin.prelogin.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.ThtText
import tht.feature.signin.R

@Composable
fun PreLoginScreen(
    loading: Boolean,
    onPhoneSignupClick: () -> Unit,
    onKakaoSignupClick: () -> Unit,
    onGoogleSignupClick: () -> Unit,
    onNaverSignupClick: () -> Unit,
    onLoginIssueClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .background(color = colorResource(id = tht.core.ui.R.color.black_161616))
                .padding(horizontal = 40.dp)
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                painter = painterResource(id = R.drawable.ic_intro_logo),
                contentDescription = "ic_intro_logo"
            )
            Spacer(modifier = Modifier.weight(1f))

            PhoneSignUpButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onPhoneSignupClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            KakaoSignUpButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onKakaoSignupClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            GoogleSignUpButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onGoogleSignupClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            NaverSignUpButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onNaverSignupClick
            )
            Spacer(modifier = Modifier.height(16.dp))

            ThtText(
                modifier = Modifier
                    .clip(RoundedCornerShape(24.dp))
                    .clickable(true, onClick = onLoginIssueClick)
                    .align(Alignment.CenterHorizontally)
                    .drawBehind {
                        val strokeWidthPx = 1.dp.toPx()
                        val verticalOffset = size.height - 2.sp.toPx()
                        drawLine(
                            color = Color.White,
                            strokeWidth = strokeWidthPx,
                            start = Offset(0f, verticalOffset),
                            end = Offset(size.width, verticalOffset)
                        )
                    }
                    .padding(horizontal = 2.dp),
                fontWeight = FontWeight.Medium,
                textSize = 12.sp,
                lineHeight = 16.8.sp,
                text = stringResource(id = R.string.message_login_issue),
                color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
            visible = loading
        )
    }
}

@Composable
@Preview
private fun PreLoginScreenPreview() {
    PreLoginScreen(
        modifier = Modifier.fillMaxSize(),
        loading = false,
        onPhoneSignupClick = {},
        onKakaoSignupClick = {},
        onGoogleSignupClick = {},
        onNaverSignupClick = {},
        onLoginIssueClick = {}
    )
}
