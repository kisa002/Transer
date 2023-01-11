package com.haeyum.android.presentation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.usecase.translation.TranslateUseCase
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.*
import java.net.SocketException
import java.nio.channels.UnresolvedAddressException

class TranslationViewModel(private val translateUseCase: TranslateUseCase) : ViewModel() {
    private val _screenState = MutableStateFlow<TranslationScreenState>(TranslationScreenState.Translating)
    val screenState = _screenState.asStateFlow()

    private val originalText = MutableStateFlow("")
    private val translatedText = originalText
        .filter { it.isNotEmpty() }
        .map {
            runCatching {
                translateUseCase(it).translatedText
            }.onFailure { exception ->
                if (exception !is CancellationException) {
                    _screenState.value = when (exception) {
                        is SocketException, is UnresolvedAddressException -> TranslationScreenState.DisconnectedNetwork
                        else -> TranslationScreenState.FailedTranslate
                    }
                }
            }.getOrNull()
        }
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = null)

    private val translatedSuccessObserver =
        originalText.combine(translatedText.filterNotNull()) { originalText, translatedText ->
            _screenState.value = TranslationScreenState.Translated(originalText, translatedText)
        }.launchIn(viewModelScope)

    fun requestTranslation(text: String) {
        originalText.value = text
    }
}