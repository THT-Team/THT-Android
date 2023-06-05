package tht.feature.tohot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import dagger.hilt.android.lifecycle.HiltViewModel
import tht.feature.tohot.StringProvider
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.state.ToHotState
import tht.feature.tohot.userData
import tht.feature.tohot.userData2
import tht.feature.tohot.userData3
import tht.feature.tohot.userData4
import java.util.Stack
import javax.inject.Inject

/**
 * - 손으로 드래그 중에 시간이 다 달면 예외 발생
 */
@HiltViewModel
class ToHotViewModel @Inject constructor(
    private val stringProvider: StringProvider
) : ViewModel(), Container<ToHotState, ToHotSideEffect> {
    private val userList = listOf(
        userData2,
        userData3,
        userData4,
        userData
    )

    override val store: Store<ToHotState, ToHotSideEffect> =
        store(
            initialState = ToHotState(
                userList = ImmutableListWrapper(userList),
                timers = ImmutableListWrapper(
                    List(userList.size) { CardTimerUiModel(5, 5, 5, false) }
                ),
                enableTimerIdx = 0
            )
        )
    private var removeUserCardStack = Stack<ToHotUserUiModel>()

    fun userChangeEvent(userIdx: Int) {
        Log.d("ToHot", "userChangeEvent => $userIdx")
        if (userIdx !in store.state.value.userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[userIdx] = this[userIdx].copy(
                                maxSec = 5,
                                currentSec = 5,
                                destinationSec = 4
                            )
                        }
                    ),
                    enableTimerIdx = userIdx
                )
            }
        }
    }

    fun userCardLoadFinishEvent(idx: Int, result: Boolean?, error: Throwable?) {
        Log.d("TAG", "userCardLoadFinishEvent => $idx, $result")
        result?.let {
            intent {
                reduce {
                    it.copy(
                        timers = ImmutableListWrapper(
                            it.timers.list.toMutableList().apply {
                                this[idx] = this[idx].copy(
                                    startAble = true
                                )
                            }
                        )
                    )
                }
            }
        }
    }

    fun ticChangeEvent(tic: Int, userIdx: Int) = with(store.state.value) {
        Log.d("ToHot", "ticChangeEvent => $tic from $userIdx => enableTimerIdx[$enableTimerIdx]")
        if (userIdx != enableTimerIdx) return@with
        if (tic <= 0) {
            when (userIdx in userList.list.indices) {
                true ->
                    intent {
                        postSideEffect(
                            ToHotSideEffect.ScrollToAndRemoveFirst(
                                scrollIdx = userIdx + 1,
                                removeIdx = userIdx
                            )
                        )
                    }
                else -> removeUserCard(userIdx)
            }
            return
        }
        if (userIdx !in userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[userIdx] = this[userIdx].copy(
                                currentSec = this[userIdx].destinationSec,
                                destinationSec = this[userIdx].destinationSec - 1
                            )
                        }
                    )
                )
            }
        }
    }

    fun removeUserCard(userIdx: Int) = with(store.state.value) {
        if (userIdx !in userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    userList = ImmutableListWrapper(
                        it.userList.list.toMutableList().apply {
                            removeUserCardStack.push(removeAt(userIdx))
                        }
                    ),
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            removeAt(userIdx)
                        }
                    ),
                    enableTimerIdx = if (enableTimerIdx >= userIdx) {
                        enableTimerIdx - 1
                    } else {
                        enableTimerIdx
                    }
                )
            }
        }
    }
}
