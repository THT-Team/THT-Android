package tht.feature.tohot.component.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.tohot.R

/**
 * 이미 주제어를 선택한 상태로 앱에 접속
 * 유저 List가 보여지기 전에 보여지는 EnterCard
 */
@Composable
fun ToHotEnterCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    ToHotNotifyCard(
        modifier = modifier,
        image = painterResource(id = R.drawable.ic_mudy_enter),
        title = stringResource(id = R.string.to_hot_enter_card_title),
        description = stringResource(id = R.string.to_hot_enter_card_description),
        buttonText = stringResource(id = R.string.entering),
        onClick = onClick
    )
}

@Composable
@Preview
private fun ToHotEnterCardPreview() {
    ToHotEnterCard()
}
