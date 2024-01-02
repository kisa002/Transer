package com.haeyum.shared.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

actual open class BaseViewModel : ViewModel() {
    actual val viewModelScope
        get() = (this as ViewModel).viewModelScope

    actual override fun onCleared() {
        super.onCleared()
    }
}