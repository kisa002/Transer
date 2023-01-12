package com.haeyum.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.usecase.recent.GetRecentTranslatesUseCase
import kotlinx.coroutines.flow.*

class RecentTranslateViewModel(private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase) : ViewModel() {
    val recentTranslates = channelFlow {
        getRecentTranslatesUseCase().collectLatest {
            send(it)
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())
}