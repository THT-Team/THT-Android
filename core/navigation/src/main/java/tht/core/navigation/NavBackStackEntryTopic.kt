package tht.core.navigation

import androidx.navigation.NavBackStackEntry
import kotlinx.coroutines.flow.MutableSharedFlow

object NavBackStackEntryTopic {
    val state = MutableSharedFlow<NavBackStackEntry>(replay = 0)
}

const val resultKey = "result"

inline fun <reified T> NavBackStackEntry.getResult(): T? {
    val result = savedStateHandle.get<T>(key = resultKey)
    return if (result is T) result else null
}
