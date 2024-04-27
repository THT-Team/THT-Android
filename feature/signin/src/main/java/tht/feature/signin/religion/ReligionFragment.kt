package tht.feature.signin.religion

import android.os.Bundle
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.extension.showToast
import tht.feature.signin.religion.composable.ReligionScreen
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel

@AndroidEntryPoint
class ReligionFragment: SignupBaseComposeFragment<ReligionViewModel>() {
    override val viewModel: ReligionViewModel by viewModels()

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
                    ReligionViewModel.ReligionSideEffect.NavigateNextView -> {
                        rootViewModel.nextEvent(SignupRootViewModel.Step.RELIGION)
                    }
                    is ReligionViewModel.ReligionSideEffect.ShowToast -> {
                        context.showToast(it.message)
                    }
                }
            }
        }
        val state by viewModel.uiStateFlow.collectAsState()
        ReligionScreen(
            modifier = Modifier.fillMaxSize(),
            loading = state.loading,
            religion = state.religion,
            onReligionClick = viewModel::onReligionClick,
            onNextClick = viewModel::onNextClick
        )
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.RELIGION)
    }
}
