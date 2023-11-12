package com.haeyum.android.presentation.main.saved

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class SavedViewModel(
    getSavedTranslatesUseCase: GetSavedTranslatesUseCase,
    private val deleteSavedTranslateUseCase: DeleteSavedTranslateUseCase
) : ViewModel() {
    val savedTranslates = channelFlow {
        getSavedTranslatesUseCase().collectLatest {
            send(it)
        }
    }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    fun deleteSavedTranslate(translatedText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteSavedTranslateUseCase(translatedText)
        }
    }
}