package tht.feature.signin.religion.composable

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import com.example.compose_ui.component.text.headline.ThtHeadline1
import tht.feature.signin.R
import tht.feature.signin.religion.ReligionUiState
import tht.feature.signin.ui.SignupDescription
import tht.feature.signin.ui.SignupSelectableButton
import tht.feature.signin.ui.SignupSmallButton

@Composable
internal fun ReligionScreen(
    loading: Boolean,
    religion: ReligionUiState.Religion?,
    onReligionClick: (ReligionUiState.Religion) -> Unit,
    onNextClick: () -> Unit,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    val enable = remember(religion) { religion != null }
    Box {
        Column(
            modifier = modifier.padding(horizontal = 38.dp)
        ) {
            Spacer(modifier = Modifier.height(40.dp))
            ThtHeadline1(
                text = remember {
                    buildAnnotatedString {
                        append(context.getString(R.string.title_religion))
                        addStyle(
                            style = SpanStyle(
                                color = Color(
                                    context.resources.getColor(tht.core.ui.R.color.white_f9fafa, null)
                                )
                            ),
                            start = 0,
                            end = 3
                        )
                    }
                },
                fontWeight = FontWeight.Bold,
                color = colorResource(id = tht.core.ui.R.color.gray_666666)
            )

            Spacer(modifier = Modifier.height(38.dp))
            Column {
                Row {
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.None,
                        title = stringResource(id = R.string.religion_none),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.None)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.Christianity,
                        title = stringResource(id = R.string.religion_christianity),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.Christianity)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.Buddhism,
                        title = stringResource(id = R.string.religion_buddhism),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.Buddhism)
                        }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row {
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.Catholic,
                        title = stringResource(id = R.string.religion_catholic),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.Catholic)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.WonBuddhism,
                        title = stringResource(id = R.string.religion_won_buddhism),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.WonBuddhism)
                        }
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    SignupSelectableButton(
                        modifier = Modifier.weight(1f),
                        isSelect = religion == ReligionUiState.Religion.Extra,
                        title = stringResource(id = R.string.religion_extra),
                        onClick = {
                            onReligionClick(ReligionUiState.Religion.Extra)
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            SignupDescription(
                modifier = Modifier.fillMaxWidth(),
                description = stringResource(id = R.string.message_can_change_in_my_page)
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
