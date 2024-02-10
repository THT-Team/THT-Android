package tht.feature.signin.signup.introduction.composable

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.compose_ui.component.font.rememberPretendardFontStyle
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.ThtText
import com.example.compose_ui.component.text.ThtTextFieldLayout
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.dpTextUnit
import com.example.compose_ui.extensions.noRippleClickable
import tht.core.ui.R
import tht.feature.signin.signup.introduction.IntroductionUiState

@Composable
fun IntroductionScreen(
    introduction: String,
    onEditIntroduction: (String) -> Unit,
    maxInputSize: Int,
    onClick: () -> Unit,
    onClear: () -> Unit,
    introductionValidation: IntroductionUiState.IntroductionValidation,
    loading: Boolean,
    modifier: Modifier = Modifier,
    onBackgroundClick: () -> Unit = {},
    focusRequester: FocusRequester = FocusRequester()
) {
    Box(
        modifier = Modifier.noRippleClickable(onBackgroundClick)
    ) {
        Column(
            modifier = modifier
                .background(colorResource(id = R.color.black_161616))
        ) {
            Column(
                modifier = modifier.padding(horizontal = 38.dp)
            ) {
                Spacer(modifier = Modifier.height(40.dp))
                val title = stringResource(id = tht.feature.signin.R.string.title_introduce)
                val highlightColor = colorResource(id = R.color.white_f9fafa)
                val titleAnnotatedString = remember {
                    buildAnnotatedString {
                        pushStyle(SpanStyle(highlightColor))
                        append(title.substring(0, 5))
                        pop()
                        append(title.substring(5, title.length))
                    }
                }
                ThtText(
                    annotatedString = titleAnnotatedString,
                    fontWeight = FontWeight.Bold,
                    color = colorResource(id = R.color.gray_666666),
                    textSize = 30.dpTextUnit
                )
                Spacer(modifier = Modifier.height(32.dp))
                val textStyle = rememberPretendardFontStyle(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 26.sp,
                    lineHeight = 33.8.sp
                )
                ThtTextFieldLayout(
                    modifier = Modifier.fillMaxWidth(),
                    value = introduction,
                    onValueChange = onEditIntroduction,
                    onClear = onClear,
                    cursorBrush = SolidColor(colorResource(id = R.color.yellow_f9cc2e)),
                    singleLine = false,
                    placeholderTextStyle = textStyle.copy(
                        color = colorResource(id = R.color.gray_8d8d8d),
                        fontWeight = FontWeight.SemiBold
                    ),
                    textStyle = textStyle.copy(
                        color = colorResource(id = R.color.yellow_f9cc2e),
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
                    placeholder = stringResource(id = tht.feature.signin.R.string.hint_introduce),
                    underLineColor = when (introductionValidation) {
                        IntroductionUiState.IntroductionValidation.IDLE ->
                            colorResource(id = R.color.gray_8d8d8d)
                        IntroductionUiState.IntroductionValidation.VALIDATE ->
                            colorResource(id = R.color.yellow_f9cc2e)
                    },
                    focusRequester = focusRequester
                )
                Spacer(modifier = Modifier.height(16.dp))
                val inputSizeText = stringResource(
                    id = tht.feature.signin.R.string.value_input_length,
                    introduction.length,
                    maxInputSize
                )
                val inputSizeTextAnnotatedString = remember(introduction) {
                    buildAnnotatedString {
                        val startIndex = inputSizeText.indexOf('/')
                        pushStyle(SpanStyle(fontWeight = FontWeight.Medium))
                        append(inputSizeText.substring(0, 1))
                        pop()
                        pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
                        append(inputSizeText.substring(1, startIndex))
                        pop()
                        pushStyle(SpanStyle(fontWeight = FontWeight.Medium))
                        append(inputSizeText.substring(startIndex))
                        pop()
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Top
                ) {
                    Image(
                        painter = painterResource(id = tht.feature.signin.R.drawable.ic_error),
                        contentDescription = "ic_error"
                    )
                    ThtP2(
                        modifier = Modifier
                            .padding(start = 6.dp)
                            .weight(1f),
                        text = stringResource(id = tht.feature.signin.R.string.message_introduce_input),
                        fontWeight = FontWeight.Medium,
                        color = colorResource(id = R.color.gray_666666),
                        textAlign = TextAlign.Start,
                        includeFontPadding = false
                    )
                    ThtText(
                        annotatedString = inputSizeTextAnnotatedString,
                        fontWeight = FontWeight.Medium,
                        textSize = 14.dpTextUnit,
                        color = colorResource(id = R.color.gray_666666),
                        textAlign = TextAlign.Start,
                        includeFontPadding = false
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    modifier = Modifier
                        .width(88.dp)
                        .height(54.dp)
                        .imePadding()
                        .align(Alignment.End),
                    shape = RoundedCornerShape(16.dp),
                    enabled = introductionValidation == IntroductionUiState.IntroductionValidation.VALIDATE,
                    onClick = onClick,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.yellow_f9cc2e),
                        contentColor = Color.Transparent,
                        disabledBackgroundColor = colorResource(id = R.color.brown_26241f),
                        disabledContentColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(id = tht.feature.signin.R.drawable.ic_right_arrow_black),
                        contentDescription = "ic_right_arrow_black",
                        tint = Color.Black
                    )
                }
                Spacer(modifier = Modifier.height(42.dp))
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
private fun IntroductionScreenPreview() {
    IntroductionScreen(
        modifier = Modifier.fillMaxSize(),
        introduction = "validate",
        maxInputSize = 12,
        onEditIntroduction = {},
        onClick = {},
        onClear = {},
        introductionValidation = IntroductionUiState.IntroductionValidation.VALIDATE,
        loading = false
    )
}
