package tht.feature.like.like

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import tht.core.ui.base.BaseStateViewModel
import tht.core.ui.base.SideEffect
import tht.core.ui.base.UiState
import tht.feature.like.like.adapter.LikeItem
import tht.feature.like.like.adapter.MockData
import javax.inject.Inject

@HiltViewModel
class LikeViewModel @Inject constructor(

) : BaseStateViewModel<LikeViewModel.LikeUiState, LikeViewModel.LikeSideEffect>() {

    override val _uiStateFlow: MutableStateFlow<LikeUiState> =
        MutableStateFlow(LikeUiState.Empty)

    private val likeItems = MutableStateFlow<LinkedHashMap<String, List<LikeModel>>>(MockData.data)

    val nextClickListener: (String) -> Unit = { nickname ->
        deleteLike(nickname)
    }

    val imageClickListener: (LikeModel) -> Unit = { likeModel ->
        postSideEffect(LikeSideEffect.ShowDetailDialog(likeModel))
    }

    init {
        viewModelScope.launch {
            likeItems.collect { map ->
                _uiStateFlow.value = if (map.isNotEmpty()) {
                    LikeUiState.NotEmpty(
                        mutableListOf<LikeItem>().apply {
                            map.forEach { (key, value) ->
                                add(LikeItem.Header(key))
                                value.forEach { add(LikeItem.Content(it)) }
                            }
                        }
                    )
                } else {
                    LikeUiState.Empty
                }
            }
        }
    }

    private fun deleteLike(nickname: String) {
        val tempMap = LinkedHashMap(likeItems.value)
        val deletedCategories = mutableListOf<String>()
        tempMap.keys.forEach { key ->
            tempMap[key] = tempMap[key]!!.filter { like ->
                like.nickname != nickname
            }
            if (tempMap[key]!!.isEmpty()) deletedCategories.add(key)
        }
        deletedCategories.forEach { tempMap.remove(it) }
        likeItems.value = tempMap
    }

    sealed class LikeUiState : UiState {
        object Empty : LikeUiState()
        data class NotEmpty(val likes: List<LikeItem>) : LikeUiState()
    }

    sealed class LikeSideEffect : SideEffect {
        data class ShowDetailDialog(val likeModel: LikeModel) : LikeSideEffect()
    }
}
