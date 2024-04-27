package tht.feature.signin.signup.moreinfo.composable

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.spacer.Spacer
import com.example.compose_ui.component.text.headline.ThtHeadline1
import com.example.compose_ui.component.text.headline.ThtHeadline4
import tht.feature.signin.R
import tht.feature.signin.signup.moreinfo.MoreInfoUiState
import tht.feature.signin.ui.SignupDescription
import tht.feature.signin.ui.SignupSelectableButton
import tht.feature.signin.ui.SignupSmallButton

@Composable
internal fun MoreInfoScreen(
    loading: Boolean,
    smoke: MoreInfoUiState.Smoke?,
    drink: MoreInfoUiState.Drink?,
    onSmokeClick: (MoreInfoUiState.Smoke) -> Unit,
    onDrinkClick: (MoreInfoUiState.Drink) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val enable = remember(drink, smoke) {
        drink != null && smoke != null
    }
    Box {
        Column(
            modifier = modifier.padding(horizontal = 38.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ThtHeadline1(
                text = remember {
                    buildAnnotatedString {
                        append(context.getString(R.string.title_more_info))
                        addStyle(
                            style = SpanStyle(color = Color(context.resources.getColor(tht.core.ui.R.color.white_f9fafa, null))),
                            start = 0,
                            end = 4
                        )
                    }
                },
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.gray_666666)
            )


            Spacer(modifier = Modifier.height(38.dp))
            ThtHeadline4(
                text = remember {
                    buildAnnotatedString {
                        append(context.getString(R.string.title_smoke_info))
                        addStyle(
                            style = SpanStyle(color = Color(context.resources.getColor(tht.core.ui.R.color.white_f9fafa, null))),
                            start = 0,
                            end = 3
                        )
                    }
                },
                fontWeight = FontWeight.Medium,
                color = colorResource(id = tht.core.ui.R.color.gray_666666)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row {
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = smoke == MoreInfoUiState.Smoke.None,
                    title = stringResource(id = R.string.title_more_info_btn_none),
                    onClick = {
                        onSmokeClick(MoreInfoUiState.Smoke.None)
                    }
                )
                Spacer(space = 16.dp)
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = smoke == MoreInfoUiState.Smoke.SomeTime,
                    title = stringResource(id = R.string.title_more_info_btn_sometime),
                    onClick = {
                        onSmokeClick(MoreInfoUiState.Smoke.SomeTime)
                    }
                )
                Spacer(space = 16.dp)
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = smoke == MoreInfoUiState.Smoke.Almost,
                    title = stringResource(id = R.string.title_more_info_btn_almost),
                    onClick = {
                        onSmokeClick(MoreInfoUiState.Smoke.Almost)
                    }
                )
            }

            Spacer(modifier = Modifier.height(64.dp))
            ThtHeadline4(
                text = remember {
                    buildAnnotatedString {
                        append(context.getString(R.string.title_drink_info))
                        addStyle(
                            style = SpanStyle(color = Color(context.resources.getColor(tht.core.ui.R.color.white_f9fafa, null))),
                            start = 0,
                            end = 3
                        )
                    }
                },
                fontWeight = FontWeight.Medium,
                color = colorResource(id = tht.core.ui.R.color.gray_666666)
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row {
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = drink == MoreInfoUiState.Drink.None,
                    title = stringResource(id = R.string.title_more_info_btn_none),
                    onClick = {
                        onDrinkClick(MoreInfoUiState.Drink.None)
                    }
                )
                Spacer(space = 16.dp)
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = drink == MoreInfoUiState.Drink.SomeTime,
                    title = stringResource(id = R.string.title_more_info_btn_sometime),
                    onClick = {
                        onDrinkClick(MoreInfoUiState.Drink.SomeTime)
                    }
                )
                Spacer(space = 16.dp)
                SignupSelectableButton(
                    modifier = Modifier.weight(1f),
                    isSelect = drink == MoreInfoUiState.Drink.Almost,
                    title = stringResource(id = R.string.title_more_info_btn_almost),
                    onClick = {
                        onDrinkClick(MoreInfoUiState.Drink.Almost)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SignupDescription(
                modifier = Modifier.fillMaxWidth(),
                description = stringResource(id = R.string.message_can_change_in_my_page),
            )

            Spacer(modifier = Modifier.weight(1f))
            SignupSmallButton(
                modifier = Modifier.align(Alignment.End),
                enable = enable,
                onClick = onNextClick
            )
            Spacer(modifier = Modifier.height(42.dp))
        }

        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            visible = loading,
            color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
        )
    }
}

