package tht.feature.signin.auth.composable

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
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
import com.example.compose_ui.component.font.pretendardFontStyle
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.caption.ThtCaption1
import com.example.compose_ui.component.text.headline.ThtHeadline1
import com.example.compose_ui.component.text.headline.ThtHeadline5
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
                modifier = Modifier.fillMaxWidth()
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
                PhoneAuthTextFieldLayout(
                    modifier = Modifier.fillMaxWidth(),
                    value = phone,
                    onValueChange = onEditPhoneNum,
                    onClear = onClear,
                    onDone = onClick,
                    hint = stringResource(id = R.string.hint_phone_input),
                    underLineColor = when (phoneValidation) {
                        PhoneAuthUiState.PhoneValidation.INVALIDATE ->
                            colorResource(id = tht.core.ui.R.color.red_ef4444)
                        PhoneAuthUiState.PhoneValidation.IDLE ->
                            colorResource(id = tht.core.ui.R.color.gray_8d8d8d)
                        PhoneAuthUiState.PhoneValidation.VALIDATE ->
                            colorResource(id = tht.core.ui.R.color.yellow_f9cc2e)
                    },
                    focusRequester = focusRequester
                )
                AnimatedVisibility(
                    visible = phoneValidation == PhoneAuthUiState.PhoneValidation.INVALIDATE
                ) {
                    ThtCaption1(
                        text = stringResource(id = R.string.message_phone_input_error),
                        fontWeight = FontWeight.Normal,
                        color = colorResource(id = tht.core.ui.R.color.red_ef4444)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_error),
                        contentDescription = "ic_error"
                    )
                    ThtCaption1(
                        modifier = Modifier.padding(start = 6.dp),
                        text = stringResource(id = R.string.message_phone_auth),
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = tht.core.ui.R.color.gray_666666),
                        textAlign = TextAlign.Start
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
private fun PhoneAuthTextFieldLayout(
    value: String,
    onValueChange: (String) -> Unit,
    underLineColor: Color,
    onDone: () -> Unit,
    onClear: () -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester()
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            PhoneAuthTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                value = value,
                hint = hint,
                onValueChange = onValueChange,
                onDone = onDone,
                focusRequester = focusRequester
            )
            if (value.isNotEmpty()) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .clickable(true, onClick = onClear),
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "ic_delete"
                )
            }
        }
        Spacer(modifier = Modifier.height(4.dp))
        Divider(
            modifier = Modifier.fillMaxWidth(),
            color = underLineColor,
            thickness = 2.dp
        )
    }
}

@Composable
private fun PhoneAuthTextField(
    value: String,
    onValueChange: (String) -> Unit,
    onDone: () -> Unit,
    hint: String,
    modifier: Modifier = Modifier,
    focusRequester: FocusRequester = FocusRequester()
) {
    val textStyle = pretendardFontStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 33.8.sp
    )
    Box(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (value.isEmpty()) 100f else 0f),
            text = hint,
            style = textStyle.copy(
                color = colorResource(id = tht.core.ui.R.color.gray_8d8d8d),
                fontWeight = FontWeight.SemiBold
            )
        )
        BasicTextField(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(if (value.isEmpty()) 0f else 100f)
                .focusRequester(focusRequester),
            value = value,
            onValueChange = onValueChange,
            textStyle = textStyle.copy(
                color = colorResource(id = tht.core.ui.R.color.yellow_f9cc2e),
                fontWeight = FontWeight.SemiBold
            ),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Phone,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onDone()
                }
            )
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
