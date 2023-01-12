package com.haeyum.android.presentation.main.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class SavedViewModel(getSavedTranslatesUseCase: GetSavedTranslatesUseCase) : ViewModel() {
    val savedTranslates = channelFlow {
        getSavedTranslatesUseCase().collectLatest {
            send(it)
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())
}