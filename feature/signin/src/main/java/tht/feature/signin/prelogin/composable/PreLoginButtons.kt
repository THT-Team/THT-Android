package tht.feature.signin.prelogin.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.text.ThtText
import tht.core.ui.R

@Composable
fun PhoneSignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(max = 52.dp)
            .background(
                color = colorResource(id = R.color.black_111111),
                shape = RoundedCornerShape(28.dp)
            )
            .clip(RoundedCornerShape(28.dp))
            .clickable(true, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = tht.feature.signin.R.drawable.ic_phone),
            contentDescription = "ic_phone"
        )
        SignupButtonText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = "핸드폰 번호로 시작하기",
            color = colorResource(id = R.color.white_f9fafa)
        )
    }
}

@Composable
fun KakaoSignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(max = 52.dp)
            .background(
                color = colorResource(id = R.color.yellow_fee500),
                shape = RoundedCornerShape(28.dp)
            )
            .clip(RoundedCornerShape(28.dp))
            .clickable(true, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = tht.feature.signin.R.drawable.ic_kakao_talk),
            contentDescription = "ic_kakao_talk"
        )
        SignupButtonText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = "카카오톡으로 시작하기",
            color = colorResource(id = R.color.black_111111)
        )
    }
}

@Composable
fun GoogleSignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(max = 52.dp)
            .background(
                color = colorResource(id = R.color.white_ffffff),
                shape = RoundedCornerShape(28.dp)
            )
            .clip(RoundedCornerShape(28.dp))
            .clickable(true, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = tht.feature.signin.R.drawable.ic_google),
            contentDescription = "ic_google"
        )
        SignupButtonText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = "Google로 시작하기",
            color = colorResource(id = R.color.black_111111)
        )
    }
}

@Composable
fun NaverSignUpButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .heightIn(max = 52.dp)
            .background(
                color = colorResource(id = R.color.green_03c75aa),
                shape = RoundedCornerShape(28.dp)
            )
            .clip(RoundedCornerShape(28.dp))
            .clickable(true, onClick = onClick)
            .padding(horizontal = 24.dp, vertical = 16.dp)
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterVertically),
            painter = painterResource(id = tht.feature.signin.R.drawable.ic_naver),
            contentDescription = "ic_naver"
        )
        SignupButtonText(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically),
            text = "네이버로 시작하기",
            color = colorResource(id = R.color.white_f9fafa)
        )
    }
}

@Composable
private fun SignupButtonText(
    modifier: Modifier = Modifier,
    color: Color,
    text: String
) {
    ThtText(
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.SemiBold,
        textSize = 16.sp,
        lineHeight = 22.4.sp,
        text = text,
        color = color
    )
}

@Composable
@Preview(showBackground = true)
private fun PreLoginButtonsPreview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.black_161616))
    ) {
        PhoneSignUpButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
        KakaoSignUpButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
        GoogleSignUpButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
        NaverSignUpButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
