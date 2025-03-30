package tht.feature.chat.chat.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun OnLifecycleEvent(
    onEvent: (
        owner: LifecycleOwner,
        event: Lifecycle.Event
    ) -> Unit
) {
    val eventHandler = rememberUpdatedState(onEvent)
    val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)

    DisposableEffect(lifecycleOwner.value) {
        val lifecycle = lifecycleOwner.value.lifecycle
        val observer = LifecycleEventObserver { owner, event ->
            eventHandler.value(owner, event)
        }

        lifecycle.addObserver(observer)
        onDispose {
            lifecycle.removeObserver(observer)
        }
    }
}

fun String.formatToAmPm(): String {
    val instant = try {
        Instant.parse(this)
    } catch (e: Exception) {
        LocalDateTime.parse(this, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSSS"))
            .atZone(ZoneId.systemDefault()).toInstant()
    }

    val formatter = DateTimeFormatter.ofPattern("hh:mm a", Locale.ENGLISH)
        .withZone(ZoneId.systemDefault())
    return formatter.format(instant)
}
