package tht.feature.signin.signup.height

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.height.composable.HeightScreen

@AndroidEntryPoint
class HeightFragment : SignupBaseComposeFragment<HeightViewModel>() {

    override val viewModel: HeightViewModel by viewModels()

    @Composable
    override fun ComposeContent() {
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
