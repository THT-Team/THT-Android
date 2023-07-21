package tht.feature.tohot.component.card

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import tht.feature.tohot.R

@Composable
fun ToHotErrorCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { }
) {
    ToHotNotifyCard(
        modifier = modifier,
        image = painterResource(id = R.drawable.ic_mudy_error),
        title = stringResource(id = R.string.to_hot_error_card_title),
        description = stringResource(id = R.string.to_hot_error_card_description),
        buttonText = stringResource(id = R.string.retry),
        onClick = onClick
    )
}

@Composable
@Preview
private fun ToHotErrorCardPreview() {
    ToHotErrorCard()
}
