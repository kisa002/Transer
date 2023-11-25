package com.haeyum.shared.presentation.mobile.saved

import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SavedViewModel(
    getSavedTranslatesUseCase: GetSavedTranslatesUseCase,
    private val deleteSavedTranslateUseCase: DeleteSavedTranslateUseCase
) : BaseViewModel() {
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