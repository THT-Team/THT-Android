package com.example.compose_ui.common.viewmodel

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface Container<State, SideEffect> {
    val store: Store<State, SideEffect>
}

interface Store<State, SideEffect> {
    val scope: CoroutineScope
    val state: StateFlow<State>
    val sideEffect: Flow<SideEffect>
    val intent: Intent<State, SideEffect>
}

interface Intent<State, SideEffect> {
    val currentState: State
    suspend fun reduce(update: (prevState: State) -> State)
    suspend fun postSideEffect(sideEffect: SideEffect)
}

private class StoreImpl<State, SideEffect>(
    initialState: State,
    override val scope: CoroutineScope,
) : Store<State, SideEffect> {
    private val _state = MutableStateFlow(initialState)
    private val _sideEffect = Channel<SideEffect>()

    override val state = _state.asStateFlow()
    override val sideEffect = _sideEffect.receiveAsFlow()

    override val intent = object : Intent<State, SideEffect> {
        override val currentState get() = state.value

        override suspend fun reduce(update: (prevState: State) -> State) {
            _state.update(update)
        }

        override suspend fun postSideEffect(sideEffect: SideEffect) {
            _sideEffect.send(sideEffect)
        }
    }
}

fun <State, SideEffect> CoroutineScope.store(initialState: State): Store<State, SideEffect> {
    return StoreImpl(
        initialState = initialState,
        scope = this,
    )
}

fun <State, SideEffect> ViewModel.store(initialState: State): Store<State, SideEffect> {
    return viewModelScope.store(initialState = initialState)
}

fun <State, SideEffect> Container<State, SideEffect>.intent(action: suspend Intent<State, SideEffect>.() -> Unit) {
    store.scope.launch { action(store.intent) }
}

@SuppressLint("ComposableNaming")
@Composable
fun <STATE : Any, SIDE_EFFECT : Any> Container<STATE, SIDE_EFFECT>.collectSideEffect(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
    sideEffect: (suspend (sideEffect: SIDE_EFFECT) -> Unit),
) {
    val sideEffectFlow = store.sideEffect
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(sideEffectFlow, lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(lifecycleState) {
            sideEffectFlow.collect(sideEffect)
        }
    }
}

@Composable
fun <STATE : Any, SIDE_EFFECT : Any> Container<STATE, SIDE_EFFECT>.collectAsState(
    lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
): State<STATE> {
    val stateFlow = store.state
    val lifecycleOwner = LocalLifecycleOwner.current

    val stateFlowLifecycleAware = remember(stateFlow, lifecycleOwner) {
        stateFlow.flowWithLifecycle(lifecycleOwner.lifecycle, lifecycleState)
    }

    @SuppressLint("StateFlowValueCalledInComposition")
    val initialValue = stateFlow.value
    return stateFlowLifecycleAware.collectAsState(initialValue)
}
