package com.haeyum.shared.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel

actual open class BaseViewModel actual constructor() {
    actual val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.Default)

    protected actual fun onCleared() {
        viewModelScope.cancel()
    }
}