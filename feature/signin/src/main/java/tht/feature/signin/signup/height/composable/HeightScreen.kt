package tht.feature.signin.signup.height.composable

import androidx.activity.compose.BackHandler
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose_ui.component.progress.ThtCircularProgress
import com.example.compose_ui.component.text.headline.ThtHeadline1
import com.example.compose_ui.component.text.p.ThtP2
import com.example.compose_ui.extensions.noRippleClickable
import tht.core.ui.R
import tht.feature.signin.signup.height.HeightUiState
import tht.feature.signin.ui.SignupDescription

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun HeightScreen(
    state: HeightUiState,
    onHeightSelectModalShow: () -> Unit,
    onHeightSelectModalHide: () -> Unit,
    onBackClick: () -> Unit,
    onClickHeightInput: () -> Unit,
    onSelectHeight: (Int) -> Unit,
    onNext: () -> Unit
) {
    val modalBottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true,
        confirmValueChange = { true }
    )

    LaunchedEffect(key1 = state.heightSelectModalShow) {
        if (state.heightSelectModalShow) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    LaunchedEffect(key1 = modalBottomSheetState) {
        snapshotFlow { modalBottomSheetState.currentValue }
            .collect {
                when (it) {
                    ModalBottomSheetValue.Expanded,
                    ModalBottomSheetValue.HalfExpanded -> onHeightSelectModalShow()

                    ModalBottomSheetValue.Hidden -> onHeightSelectModalHide()
                }
            }
    }

    BackHandler(
        enabled = true,
        onBack = onBackClick
    )

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        HeightSelectModel(
            modifier = Modifier.fillMaxSize(),
            modalBottomSheetState = modalBottomSheetState,
            onSelectHeight = onSelectHeight,
        ) {
            HeightScreenContent(
                modifier = Modifier.fillMaxSize(),
                height = state.height,
                onClickHeightInput = onClickHeightInput,
                onNext = onNext
            )
        }
        ThtCircularProgress(
            modifier = Modifier.align(Alignment.Center),
            color = colorResource(id = R.color.yellow_f9cc2e),
            visible = state.loading
        )
    }
}

@Composable
private fun HeightScreenContent(
    height: Int?,
    onClickHeightInput: () -> Unit,
    onNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val heightString = remember(height) {
        if (height == null) {
            "145 cm"
        } else {
            "$height cm"
        }
    }
    Column(
        modifier = modifier
            .background(colorResource(id = R.color.black_161616))
            .padding(horizontal = 38.dp)
            .noRippleClickable(onClickHeightInput)
    ) {
        Spacer(modifier = Modifier.height(40.dp))
        ThtHeadline1(
            text = stringResource(id = tht.feature.signin.R.string.title_height),
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.white_f9fafa)
        )

        Spacer(modifier = Modifier.height(32.dp))
        ThtHeadline1(
            text = heightString,
            fontWeight = FontWeight.Bold,
            color = colorResource(
                id = if (height == null) {
                    R.color.brown_26241f
                } else {
                    R.color.yellow_f9cc2e
                }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))
        SignupDescription(
            modifier = Modifier.fillMaxWidth(),
            description = stringResource(id = tht.feature.signin.R.string.message_can_change_in_my_page),
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .width(88.dp)
                .height(54.dp)
                .imePadding()
                .align(Alignment.End),
            shape = RoundedCornerShape(16.dp),
            enabled = height != null,
            onClick = onNext,
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

@Composable
@Preview
private fun HeightScreenPreview() {
    HeightScreen(
        state = HeightUiState.DEFAULT,
        onHeightSelectModalHide = {},
        onHeightSelectModalShow = {},
        onBackClick = {},
        onClickHeightInput = {},
        onSelectHeight = {},
        onNext = {}
    )
}
