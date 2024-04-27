package tht.feature.signin.signup.moreinfo

import android.os.Bundle
import android.view.View
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.extension.showToast
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.moreinfo.composable.MoreInfoScreen

@AndroidEntryPoint
class MoreInfoFragment : SignupBaseComposeFragment<MoreInfoViewModel>() {
    override val viewModel: MoreInfoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.fetchSavedData(rootViewModel.phone.value)
    }

    @Composable
    override fun ComposeContent() {
        val context = LocalContext.current
        LaunchedEffect(Unit) {
            viewModel.sideEffectFlow.collect {
                when (it) {
                    is MoreInfoViewModel.MoreInfoSideEffect.NavigateNextView -> {
                        rootViewModel.nextEvent(SignupRootViewModel.Step.MORE_INFO)
                    }

                    is MoreInfoViewModel.MoreInfoSideEffect.ShowToast -> {
                        context.showToast(it.message)
                    }
                }
            }
        }

        val state by viewModel.uiStateFlow.collectAsState()
        MoreInfoScreen(
            loading = state.loading,
            smoke = state.smoke,
            drink = state.drink,
            onSmokeClick = viewModel::onSmokeClick,
            onDrinkClick = viewModel::onDrinkClick,
            onNextClick = viewModel::onNextClick
        )

    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.MORE_INFO)
    }
}
