package com.haeyum.android.presentation.main.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class RecentTranslateViewModel(private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase) : ViewModel() {
    val recentTranslates = channelFlow {
        getRecentTranslatesUseCase().collectLatest {
            send(it)
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())
}