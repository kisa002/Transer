package com.haeyum.android.presentation.main.recent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.shared.domain.usecase.recent.DeleteRecentTranslateByTranslatedTextUseCase
import com.haeyum.shared.domain.usecase.recent.GetRecentTranslatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecentTranslateViewModel(
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val deleteRecentTranslateByTranslatedTextUseCase: DeleteRecentTranslateByTranslatedTextUseCase
) : ViewModel() {
    val recentTranslates = channelFlow {
        getRecentTranslatesUseCase().collectLatest {
            send(it)
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    fun deleteRecentTranslateByTranslatedText(translatedText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentTranslateByTranslatedTextUseCase(translatedText)
        }
    }
}