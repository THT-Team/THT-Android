package tht.feature.like.detail

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import javax.inject.Inject

@HiltViewModel
class LikeDetailViewModel @Inject constructor(

) : BaseStateViewModel<LikeDetailViewModel.LikeDetailUiState, LikeDetailViewModel.LikeDetailSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LikeDetailUiState> =
        MutableStateFlow(LikeDetailUiState.Default)

    fun showReportDialogEvent() =
        postSideEffect(LikeDetailSideEffect.ShowReportDialog)

    fun reportEvent(nickname: String) =
        postSideEffect(LikeDetailSideEffect.Report(nickname))

    fun blockEvent(nickname: String) =
        postSideEffect(LikeDetailSideEffect.Block(nickname))

    sealed class LikeDetailUiState : UiState {
        object Default : LikeDetailUiState()
    }

    sealed class LikeDetailSideEffect : SideEffect {
        object ShowReportDialog : LikeDetailSideEffect()
        data class Report(val nickname: String) : LikeDetailSideEffect()
        data class Block(val nickname: String) : LikeDetailSideEffect()
    }
}
