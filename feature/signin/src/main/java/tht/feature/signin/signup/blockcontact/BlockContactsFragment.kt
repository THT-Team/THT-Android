package tht.feature.signin.signup.blockcontact

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tht.core.ui.extension.showToast
import tht.feature.signin.signup.SignupBaseComposeFragment
import tht.feature.signin.signup.SignupRootViewModel
import tht.feature.signin.signup.blockcontact.composable.BlockContactsScreen

@AndroidEntryPoint
class BlockContactsFragment : SignupBaseComposeFragment<BlockContactsViewModel>() {

    override val viewModel: BlockContactsViewModel by viewModels()

    @Composable
    override fun ComposeContent() {
        LaunchedEffect(Unit) {
            viewModel.sideEffectFlow.collect {
                when (it) {
                    is BlockContactsViewModel.BlockContactsSideEffect.ShowToast -> {
                        requireContext().showToast(it.message)
                    }
                    BlockContactsViewModel.BlockContactsSideEffect.NavigateNextScreen -> {
                        rootViewModel.nextEvent(SignupRootViewModel.Step.BlockContacts)
                    }
                }
            }
        }

        val state by viewModel.uiStateFlow.collectAsState()
        BlockContactsScreen(
            loading = state.loading,
            btnEnable = !state.loading,
            onBlockContactsClick = viewModel::onBlockContactsEvent,
            onLaterClick = viewModel::onLaterEvent
        )
    }

    override fun setProgress() {
        rootViewModel.progressEvent(SignupRootViewModel.Step.BlockContacts)
    }
}
