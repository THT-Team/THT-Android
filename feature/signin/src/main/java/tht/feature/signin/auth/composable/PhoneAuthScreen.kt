package tht.feature.signin.auth.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.font.rememberPretendardFontStyle
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.ThtTextFieldLayout
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.headline.ThtHeadline1
import com.example.compose_ui.component.text.headline.ThtHeadline5
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.component.toolbar.ThtToolbar
import com.example.compose_ui.extensions.noRippleClickable
import tht.feature.signin.R
import tht.feature.signin.auth.PhoneAuthUiState

//https://blog.msg-team.com/android-jetpack-compose-inserting-components-on-a-soft-keyboard-fb988dbfb20b
@Composable
fun PhoneAuthScreen(
    phone: String,
    onEditPhoneNum: (String) -> Unit,
    onClick: () -> Unit,
    onClear: () -> Unit,
    phoneValidation: PhoneAuthUiState.PhoneValidation,
    loading: Boolean,
    modifier: Modifier = Modifier,
    onBackgroundClick: () -> Unit = {},
    onBackClick: (() -> Unit)? = null,
    focusRequester: FocusRequester = FocusRequester()
) {
    Box(
        modifier = Modifier.noRippleClickable(onBackgroundClick)
    ) {
        Column(
            modifier = modifier
                .background(colorResource(id = tht.core.ui.R.color.black_161616))
        ) {
            ThtToolbar(
                modifier = Modifier
                    .fillMaxWidth()
                    .systemBarsPadding(),
                onBackPressed = onBackClick,
                content = {}
            )
            Column(
                modifier = modifier.padding(horizontal = 38.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                ThtHeadline1(
                    text = stringResource(id = R.string.phone_auth),
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = tht.core.ui.R.color.white_f9fafa)
                )
                Spacer(modifier = Modifier.height(32.dp))
                val textStyle = rememberPretendardFontStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    lineHeight = 33.8.sp
                )
                ThtTextFieldLayout(
                    modifier = Modifier.fillMaxWidth(),
                    value = phone,
                    onValueChange = onEditPhoneNum,
                    onClear = onClear,
                    cursorBrush = SolidColor(colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)),
                    singleLine = true,
                    placeholderTextStyle = textStyle.copy(
                        color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d),
                        fontWeight = FontWeight.SemiBold
                    ),
                    textStyle = textStyle.copy(
                        color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                        fontWeight = FontWeight.SemiBold
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            onClick()
                        }
                    ),
                    placeholder = stringResource(id = R.string.hint_phone_input),
                    underLineColor = when (phoneValidation) {
                        PhoneAuthUiState.PhoneValidation.INVALIDATE ->
                            colorResource(id = tht.core.ui.R.color.red_ef4444)
                        PhoneAuthUiState.PhoneValidation.IDLE ->
                            colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
                        PhoneAuthUiState.PhoneValidation.VALIDATE ->
                            colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
                    },
                    focusRequester = focusRequester,
                    errorVisible = phoneValidation == PhoneAuthUiState.PhoneValidation.INVALIDATE,
                    error = {
                        ThtCaption1(
                            text = stringResource(id = R.string.message_phone_input_error),
                            fontWeight = FontWeight.Normal,
                            color = colorResource(id = tht.core.ui.R.color.red_ef4444)
                        )
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = "ic_error"
                    )
                    ThtP2(
                        modifier = Modifier.padding(start = 6.dp),
                        text = stringResource(id = R.string.message_phone_auth),
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = tht.core.ui.R.color.gray_666666),
                        textAlign = TextAlign.Start,
                        includeFontPadding = false
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .imePadding(),
                    shape = RoundedCornerShape(16.dp),
                    enabled = phoneValidation == PhoneAuthUiState.PhoneValidation.VALIDATE,
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                        contentColor = Color.Transparent,
                        disabledBackgroundColor = colorResource(id = tht.core.ui.R.color.brown_26241f),
                        disabledContentColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    ThtHeadline5(
                        text = stringResource(id = R.string.do_auth),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id = tht.core.ui.R.color.black_222222)
                    )
                }
                Spacer(modifier = Modifier.height(42.dp))
            }
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
private fun PhoneAuthScreenPreview() {
    PhoneAuthScreen(
        modifier = Modifier.fillMaxSize(),
        phone = "01011111111",
        onEditPhoneNum = {},
        onClick = {},
        onClear = {},
        phoneValidation = PhoneAuthUiState.PhoneValidation.VALIDATE,
        loading = false
    )
}

@Composable
@Preview
private fun PhoneAuthScreenWarningPreview() {
    PhoneAuthScreen(
        modifier = Modifier.fillMaxSize(),
        phone = "01011111111",
        onEditPhoneNum = {},
        onClick = {},
        onClear = {},
        phoneValidation = PhoneAuthUiState.PhoneValidation.VALIDATE,
        loading = false
    )
}
