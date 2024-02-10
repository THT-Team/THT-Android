package tht.feature.signin.signup.introduction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import tht.core.ui.extension.showToast
import tht.feature.signin.R
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.introduction.composable.IntroductionScreen

@AndroidEntryPoint
class IntroductionFragment : SignupBaseComposeFragment<IntroductionViewModel>() {

    override val viewModel by viewModels<IntroductionViewModel>()

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.INTRODUCTION)
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    override fun ComposeContent() {
        val keyboard = LocalSoftwareKeyboardController.current
        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(key1 = Unit) {
            viewModel.sideEffectFlow.collect {
                when (it) {
                    is IntroductionViewModel.IntroductionSideEffect.ShowToast ->
                        context?.showToast(it.message)

                    is IntroductionViewModel.IntroductionSideEffect.KeyboardVisible ->
                        if (it.visible) keyboard?.show() else keyboard?.hide()

                    is IntroductionViewModel.IntroductionSideEffect.NavigateNextView ->
                        rootViewModel.nextEvent(SignupRootViewModel.Step.INTRODUCTION)
                }
            }
        }
        val state by viewModel.uiStateFlow.collectAsState()
        if (state.invalidatePhone) {
            requireContext().showToast(getString(R.string.message_invalidate_phone))
            rootViewModel.backEvent()
        } else {
            IntroductionScreen(
                introduction = state.introduction,
                onEditIntroduction = viewModel::onTextInputEvent,
                onClick = viewModel::onClickNextEvent,
                onClear = viewModel::onClear,
                maxInputSize = state.maxLength,
                introductionValidation = state.validation,
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
    }

    companion object {

        val TAG = IntroductionFragment::class.simpleName.toString()

        fun newInstance() = IntroductionFragment()
    }
}
