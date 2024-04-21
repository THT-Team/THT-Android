package tht.feature.signin.signup.height

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.extension.showToast
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.height.composable.HeightScreen

@AndroidEntryPoint
class HeightFragment : SignupBaseComposeFragment<HeightViewModel>() {

    override val viewModel: HeightViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    @Composable
    override fun ComposeContent() {
        LaunchedEffect(key1 = Unit) {
            viewModel.sideEffectFlow.collect {
                when (it) {
                    is HeightViewModel.HeightSideEffect.ShowToast -> requireContext().showToast(it.message)

                    is HeightViewModel.HeightSideEffect.NavigateNextView -> {
                        rootViewModel.nextEvent(SignupRootViewModel.Step.HEIGHT)
                    }
                }
            }
        }
        val state by viewModel.uiStateFlow.collectAsState()
        HeightScreen(
            state = state,
            onHeightSelectModalShow = viewModel::onHeightSelectModalShow,
            onHeightSelectModalHide = viewModel::onHeightSelectModalHide,
            onBackClick = viewModel::onBackClick,
            onClickHeightInput = viewModel::onClickHeightInput,
            onSelectHeight = viewModel::onSelectHeight,
            onNext = viewModel::onNext
        )
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.HEIGHT)
    }
}
