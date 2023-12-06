package com.haeyum.shared.presentation.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*

@Stable
class EventSnackbarState(
    coroutineScope: CoroutineScope,
    initialMessages: List<String>,
    private val extraBuffer: Int,
) {
    private val _messages = MutableStateFlow(initialMessages)
    val messages = _messages.asStateFlow()

    val message = _messages
        .map {
            it.firstOrNull()
        }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, initialValue = null)

    val visibility = _messages
        .map {
            it.getOrNull(0)
        }
        .filterNotNull()
        .transform {
            println("_messages.value: ${_messages.value}")
            emit(true)
            delay(2000)
            emit(false)
            delay(300)

            _messages.update {
                it.drop(1)
            }
        }
        .stateIn(scope = coroutineScope, started = SharingStarted.WhileSubscribed(5000), initialValue = false)

    fun showSnackbar(message: String) {
        _messages.update {
            it.takeLast(extraBuffer - 1) + message
        }
    }

    companion object {
        fun saver(
            coroutineScope: CoroutineScope,
            initialMessages: List<String>,
            maxBuffer: Int
        ): Saver<EventSnackbarState, *> = Saver(
            save = {
                it._messages.value
            },
            restore = {
                EventSnackbarState(coroutineScope, initialMessages, maxBuffer)
            }
        )
    }
}

@Composable
fun rememberEventSnackbraState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    initialMessages: List<String> = emptyList(),
    maxBuffer: Int = 2
) = rememberSaveable(
    coroutineScope,
    initialMessages,
    saver = EventSnackbarState.saver(coroutineScope, initialMessages, maxBuffer)
) {
    EventSnackbarState(coroutineScope, initialMessages, maxBuffer)
}