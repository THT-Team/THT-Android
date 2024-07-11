package tht.feature.signin.signup.blockcontact

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import javax.inject.Inject

@HiltViewModel
class BlockContactsViewModel @Inject constructor() :
    BaseStateViewModel<BlockContactsUiState, BlockContactsViewModel.BlockContactsSideEffect>() {

    sealed interface BlockContactsSideEffect : SideEffect {
        object NavigateNextScreen : BlockContactsSideEffect

        data class ShowToast(
            val message: String
        ) : BlockContactsSideEffect
    }

    override val _uiStateFlow: MutableStateFlow<BlockContactsUiState> = MutableStateFlow(BlockContactsUiState.default)

    fun onBlockContactsEvent() {
        viewModelScope.launch {
            _uiStateFlow.update { it.copy(loading = true) }
            delay(1500L) // 기능 구현 전 delay
            postSideEffect(BlockContactsSideEffect.ShowToast("저장된 연락처를 모두 차단했습니다"))
            postSideEffect(BlockContactsSideEffect.NavigateNextScreen)
            _uiStateFlow.update { it.copy(loading = true) }
        }
    }

    fun onLaterEvent() {
        postSideEffect(BlockContactsSideEffect.NavigateNextScreen)
    }
}
