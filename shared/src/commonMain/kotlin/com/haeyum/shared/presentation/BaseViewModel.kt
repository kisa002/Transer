package com.haeyum.shared.presentation

import kotlinx.coroutines.CoroutineScope

expect open class BaseViewModel() {
    val viewModelScope: CoroutineScope

    protected fun onCleared()
}