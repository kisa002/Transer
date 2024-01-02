package com.haeyum.shared.presentation.mobile.recent

import com.haeyum.shared.domain.usecase.recent.DeleteRecentTranslateByTranslatedTextUseCase
import com.haeyum.shared.domain.usecase.recent.GetRecentTranslatesUseCase
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class RecentTranslateViewModel(
    private val getRecentTranslatesUseCase: GetRecentTranslatesUseCase,
    private val deleteRecentTranslateByTranslatedTextUseCase: DeleteRecentTranslateByTranslatedTextUseCase
) : BaseViewModel() {
    val recentTranslates = getRecentTranslatesUseCase()
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    fun deleteRecentTranslateByTranslatedText(translatedText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteRecentTranslateByTranslatedTextUseCase(translatedText)
        }
    }
}