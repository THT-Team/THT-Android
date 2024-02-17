package tht.feature.setting.composable.mypage

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.text.headline.ThtHeadline5
import tht.feature.setting.R

@Composable
fun MyPageNicknameRow(
    nickname: String,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
    ) {
        ThtHeadline5(
            text = nickname,
            fontWeight = FontWeight.SemiBold,
            color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
        )
        Spacer(modifier = Modifier.width(10.dp))
        IconButton(
            onClick = onEditClick
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_nickname_edit),
                contentDescription = "ic_nickname_edit",
                tint = colorResource(id = tht.core.ui.R.color.white_ffffff)
            )
        }
    }
}

@Composable
@Preview
private fun MyPageNicknameRowPreview() {
    MyPageNicknameRow(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        nickname = "nickname",
        onEditClick = {}
    )
}
