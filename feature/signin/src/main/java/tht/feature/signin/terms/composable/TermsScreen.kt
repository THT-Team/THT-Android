package tht.feature.signin.terms.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.toolbar.ThtToolbar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import tht.core.ui.R
import tht.feature.signin.terms.uimodel.TermsUiModel
import tht.feature.signin.ui.SignupLargeButton

@Composable
internal fun TermsScreen(
    isAllSelect: Boolean,
    loading: Boolean,
    btnEnable: Boolean,
    terms: ImmutableList<TermsUiModel>,
    onTermsClick: (TermsUiModel, Int) -> Unit,
    onTermsLinkClick: (String?) -> Unit,
    onAllSelectClick: () -> Unit,
    onNext: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.black_161616))
        ) {
            ThtToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
                onBackPressed = onBackClick,
                content = {}
            )
            Column(
                modifier = modifier
                    .padding(horizontal = 28.dp)
            ) {
                //TODO: Toolbar
                Spacer(modifier = Modifier.height(26.dp))
                Image(
                    painter = painterResource(id = tht.feature.signin.R.drawable.ic_falling_logo_72_width),
                    contentDescription = "ic_falling_logo_72_width"
                )

                Spacer(modifier = Modifier.height(52.dp))
                ThtHeadline5(
                    text = stringResource(id = tht.feature.signin.R.string.title_terms),
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(id = R.color.white_f9fafa)
                )

                Spacer(modifier = Modifier.height(68.dp))

                terms.forEachIndexed { index, termsUiModel ->
                    TermsRow(
                        terms = termsUiModel,
                        onClick = remember(termsUiModel) {
                            { onTermsClick(termsUiModel, index) }
                        },
                        onRightArrowClick = remember(termsUiModel.link) {
                            { onTermsLinkClick(termsUiModel.link) }
                        }
                    )
                    if (index != terms.size - 1) {
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                SignupLargeSelectButton(
                    text = stringResource(id = tht.feature.signin.R.string.title_all_agreement),
                    isSelect = isAllSelect,
                    enable = !loading,
                    onClick = onAllSelectClick
                )
                Spacer(modifier = Modifier.height(16.dp))
                //button 2개
                SignupLargeButton(
                    text = stringResource(id = tht.feature.signin.R.string.starting),
                    enable = btnEnable,
                    onClick = onNext
                )

                Spacer(modifier = Modifier.height(4.dp))
                ThtCaption1(
                    text = stringResource(id = tht.feature.signin.R.string.message_terms_notify),
                    fontWeight = FontWeight.Normal,
                    color = colorResource(id = R.color.gray_666666),
                    textAlign = TextAlign.Start,
                    lineHeight = 15.4.sp
                )
                Spacer(modifier = Modifier.height(40.dp))
            }
        }
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_f9cc2e),
            visible = loading
        )
    }
}

@Composable
@Preview
private fun TermsScreenPreview() {
    TermsScreen(
        isAllSelect = true,
        loading = false,
        btnEnable = true,
        terms = persistentListOf(
            TermsUiModel(
                title = "long title long title long title long title long title long title long title",
                key = "key",
                description = "폴링에서 제공하는 이벤트/혜택 등 다양한 정보를 Push 알림으로\n받아보실 수 있습니다.",
                require = true,
                link = "adsad",
                isSelect = true
            )
        ),
        onTermsClick = { _, _ -> },
        onTermsLinkClick = {},
        onAllSelectClick = { },
        onNext = { },
        onBackClick = { }
    )
}
