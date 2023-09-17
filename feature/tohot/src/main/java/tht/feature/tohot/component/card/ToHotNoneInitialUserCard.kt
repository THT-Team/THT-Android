package tht.feature.tohot.component.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.tohot.R

/**
 * 처음 주제어를 선택 시 유저가 없는 경우
 * - 기다리는 무디 조회 버튼
 */
@Composable
fun ToHotNoneInitialUserCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    ToHotNotifyCard(
        modifier = modifier,
        image = painterResource(id = R.drawable.ic_mudy_none_initial_user),
        title = stringResource(id = R.string.to_hot_none_initial_user_card_title),
        description = stringResource(id = R.string.to_hot_none_initial_user_card_description),
        buttonText = stringResource(id = R.string.waiting),
        onClick = onClick
    )
}

@Composable
@Preview
private fun ToHotNoneInitialUserCardPreview() {
    ToHotNoneInitialUserCard()
}
