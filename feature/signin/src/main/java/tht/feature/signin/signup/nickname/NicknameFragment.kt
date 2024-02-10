package tht.feature.signin.signup.nickname

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import tht.core.ui.extension.showToast
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.nickname.composable.NicknameScreen

/**
 * TODO: Toolbar 동일하게 맞추기
 * TODO: Button Up
 */
@AndroidEntryPoint
class NicknameFragment : SignupBaseComposeFragment<NicknameViewModel>() {

    override val viewModel by viewModels<NicknameViewModel>()

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun ComposeContent() {
        val keyboard = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(key1 = Unit) {
            viewModel.sideEffectFlow.collect {
                when (it) {
                    is NicknameViewModel.NicknameSideEffect.ShowToast ->
                        context?.showToast(it.message)

                    is NicknameViewModel.NicknameSideEffect.KeyboardVisible ->
                        if (it.visible) keyboard?.show() else keyboard?.hide()

                    is NicknameViewModel.NicknameSideEffect.NavigateNextView ->
                        rootViewModel.nextEvent(SignupRootViewModel.Step.NICKNAME)
                }
            }
        }

        val state by viewModel.uiStateFlow.collectAsState()
        NicknameScreen(
            modifier = Modifier.fillMaxSize(),
            nickname = state.nickname,
            nicknameValidation = state.validation,
            maxInputSize = state.maxLength,
            onEditNickname = viewModel::onTextInputEvent,
            onClick = viewModel::onClickNextEvent,
            onClear = viewModel::onClearEvent,
            loading = state.loading,
            onBackgroundClick = viewModel::onBackgroundTouchEvent,
            focusRequester = focusRequester
        )

        LaunchedEffect(key1 = Unit) {
            focusRequester.requestFocus()
            delay(100)
            keyboard?.show()
        }
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.NICKNAME)
    }

    companion object {

        val TAG = NicknameFragment::class.simpleName.toString()

        fun newInstance() = NicknameFragment()
    }
}
