package tht.feature.like.detail

import androidx.lifecycle.SavedStateHandle
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.like.like.LikeModel
import javax.inject.Inject

@HiltViewModel
class LikeDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : BaseStateViewModel<LikeDetailViewModel.LikeDetailUiState, LikeDetailViewModel.LikeDetailSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LikeDetailUiState> =
        MutableStateFlow(LikeDetailUiState.Default)

    val user = savedStateHandle.getStateFlow(
        LikeDetailFragment.LIKE_DETAIL_KEY,
        LikeModel.getDefaultLikeModel()
    )

    fun showReportOrBlockDialogEvent() =
        postSideEffect(LikeDetailSideEffect.ShowReportOrBlockDialog)

    fun showReportDialogEvent() =
        postSideEffect(LikeDetailSideEffect.ShowReportDialog)

    fun blockEvent() =
        postSideEffect(LikeDetailSideEffect.ShowBlockDialog)

    fun dismissEvent() =
        postSideEffect(LikeDetailSideEffect.Dismiss)

    fun blockUser() {}

    fun reportUser(reportReason: String) {}

    sealed class LikeDetailUiState : UiState {
        object Default : LikeDetailUiState()
    }

    sealed class LikeDetailSideEffect : SideEffect {
        object ShowReportOrBlockDialog : LikeDetailSideEffect()
        object ShowReportDialog : LikeDetailSideEffect()
        object ShowBlockDialog : LikeDetailSideEffect()
        object Dismiss : LikeDetailSideEffect()
    }
}
