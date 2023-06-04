package tht.feature.tohot.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose_ui.common.viewmodel.Container
import com.example.compose_ui.common.viewmodel.Store
import com.example.compose_ui.common.viewmodel.intent
import com.example.compose_ui.common.viewmodel.store
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import tht.feature.tohot.StringProvider
import tht.feature.tohot.model.CardTimerUiModel
import tht.feature.tohot.model.ImmutableListWrapper
import tht.feature.tohot.model.ToHotUserUiModel
import tht.feature.tohot.model.topics
import tht.feature.tohot.state.ToHotSideEffect
import tht.feature.tohot.state.ToHotState
import tht.feature.tohot.userData
import tht.feature.tohot.userData2
import tht.feature.tohot.userData3
import tht.feature.tohot.userData4
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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
                loading = false,
                userList = ImmutableListWrapper(emptyList()),
                timers = ImmutableListWrapper(emptyList()),
                enableTimerIdx = 0,
                selectTopic = null,
                topicModalShow = false,
                topicList = ImmutableListWrapper(emptyList()),
                topicSelectRemainingTime = "00:00:00",
                topicSelectRemainingTimeMill = 0
            )
        )
    private var removeUserCardStack = Stack<ToHotUserUiModel>()

    init {
        toHotLogic()
    }

    private fun toHotLogic() {
        with(store.state.value) {
            when {
                topicList.list.isEmpty() -> fetchTopicList()

                selectTopic == null  -> {
                    clearUserCard()
                    intent {
                        reduce { it.copy(topicModalShow = true) }
                    }
                }

                userList.list.isEmpty() -> requestUserCard(selectTopic.key)
            }
        }
    }

    private fun fetchTopicList() {
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        topicList = ImmutableListWrapper(topics),
                        topicModalShow = true,
                        topicSelectRemainingTime = "24:00:00"
                    )
                }
                reduce { it.copy(loading = false) }
            }
            toHotLogic()
        }
    }

    fun openTopicSelectEvent() {
        startTopicRemainingTimer()
    }

    fun closeTopicSelectEvent() {
        if (::topicRemainingTimer.isInitialized) topicRemainingTimer.cancel()
    }

    /**
     * 1 초 마다 LocalDateTime 객체, State 객체를 생성 하는 문제 존재
     * 미완성
     */
    private lateinit var topicRemainingTimer: Job
    private fun startTopicRemainingTimer() {
        if (::topicRemainingTimer.isInitialized) topicRemainingTimer.cancel()
        topicRemainingTimer = viewModelScope.launch(Dispatchers.IO) {
            with (store.state.value) {
                while (isActive && topicSelectRemainingTimeMill >= 0) {
                    delay(1000)
                    val remainingString = (topicSelectRemainingTimeMill - System.currentTimeMillis()).let {
                        val date = LocalDateTime.ofInstant(Instant.ofEpochMilli(it), ZoneId.systemDefault())
                        date.format(DateTimeFormatter.ofPattern("HH:mm:ss"))
                    }
                    intent {
                        reduce {
                            it.copy(
                                topicSelectRemainingTime = remainingString,
                                topicSelectRemainingTimeMill = topicSelectRemainingTimeMill - 1000
                            )
                        }
                    }
                }

                if (topicSelectRemainingTimeMill < 0) {
                    fetchTopicList()
                }
            }
        }
    }

    private fun clearUserCard() {
        intent {
            reduce {
                it.copy(
                    userList = ImmutableListWrapper(emptyList()),
                    timers = ImmutableListWrapper(emptyList())
                )
            }
        }
    }

    private fun requestUserCard(topicKey: Long) {
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        userList = ImmutableListWrapper(userList),
                        timers = ImmutableListWrapper(
                            List(userList.size) {
                                CardTimerUiModel(MAX_TIMER_SEC, MAX_TIMER_SEC, MAX_TIMER_SEC)
                            }
                        )
                    )
                }
                reduce { it.copy(loading = false) }
            }
        }
    }

    fun topicSelectEvent(topicKey: Long) {
        intent {
            reduce {
                it.copy(
                    selectTopic = it.topicList.list.firstOrNull { t -> t.key == topicKey }
                )
            }
        }
    }

    fun topicSelectFinishEvent() {
        viewModelScope.launch {
            intent { reduce { it.copy(loading = true) } }
            delay(500)
            intent {
                reduce {
                    it.copy(
                        topicModalShow = false
                    )
                }
                reduce { it.copy(loading = false) }
            }
            toHotLogic()
        }
    }

    fun userChangeEvent(userIdx: Int) {
        Log.d("ToHot", "userChangeEvent => $userIdx")
        if (userIdx !in store.state.value.userList.list.indices) return
        intent {
            reduce {
                it.copy(
                    timers = ImmutableListWrapper(
                        it.timers.list.toMutableList().apply {
                            this[userIdx] = this[userIdx].copy(
                                maxSec = MAX_TIMER_SEC,
                                currentSec = MAX_TIMER_SEC,
                                destinationSec = MAX_TIMER_SEC - 1
                            )
                        }
                    ),
                    enableTimerIdx = userIdx
                )
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

    companion object {
        private const val MAX_TIMER_SEC = 5
    }
}
