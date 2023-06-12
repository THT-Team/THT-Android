package tht.feature.like.like

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
        MutableStateFlow(LikeUiState.Default)

    private val likeResponse = MutableStateFlow<MutableMap<String, List<LikeModel>>>(MockData.data)
    private val _likeList = MutableStateFlow<List<LikeItem>>(emptyList())
    val likeList: StateFlow<List<LikeItem>> get() = _likeList

    init {
        viewModelScope.launch {
            likeResponse.collect { map ->
                val list = mutableListOf<LikeItem>().apply {
                    map.forEach { (key, value) ->
                        add(LikeItem.Header(key))
                        value.forEach { add(LikeItem.Content(it)) }
                    }
                }
                _likeList.value = list
            }
        }
    }

    fun deleteLike(nickname: String) {
        val tempMap = HashMap(likeResponse.value)
        val keys = tempMap.keys

        keys.forEach { key ->
            tempMap[key] = tempMap[key]!!.filter { like ->
                like.nickname != nickname
            }
            if (tempMap[key]!!.isEmpty()) tempMap.remove(key)
        }
        likeResponse.value = tempMap
    }

    sealed class LikeUiState : UiState {
        object Default : LikeUiState()
    }

    sealed class LikeSideEffect : SideEffect
}
