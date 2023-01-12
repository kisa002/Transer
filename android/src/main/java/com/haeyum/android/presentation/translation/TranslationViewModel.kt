package com.haeyum.android.presentation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.usecase.recent.AddRecentTranslateUseCase
import com.haeyum.common.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.common.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.common.domain.usecase.saved.IsExistsSavedTranslateUseCase
import com.haeyum.common.domain.usecase.translation.TranslateUseCase
import io.ktor.util.network.*
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.net.SocketException

class TranslationViewModel(
    private val translateUseCase: TranslateUseCase,
    private val isExistsSavedTranslateUseCase: IsExistsSavedTranslateUseCase,
    private val addSavedTranslateUseCase: AddSavedTranslateUseCase,
    private val getSavedTranslateUseCase: GetSavedTranslatesUseCase,
    private val deleteSavedTranslateUseCase: DeleteSavedTranslateUseCase,
    private val addRecentTranslateUseCase: AddRecentTranslateUseCase
) : ViewModel() {
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
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = null)

    private val savedTranslates = channelFlow {
        getSavedTranslateUseCase().collectLatest {
            send(it)
        }
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = emptyList())

    val isExistsSavedTranslate = translatedText.combine(savedTranslates) { translatedText, savedTranslates ->
        translatedText
    }
        .filterNotNull()
        .flatMapLatest {
            isExistsSavedTranslateUseCase(it)
        }
        .flowOn(Dispatchers.IO)
        .stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, initialValue = false)

    private val translatedSuccessObserver =
        originalText.combine(translatedText.filterNotNull()) { originalText, translatedText ->
            originalText to translatedText
        }.onEach { (originalText, translatedText) ->
            _screenState.value = TranslationScreenState.Translated(originalText, translatedText)
            addRecentTranslateUseCase(originalText, translatedText)
        }.launchIn(viewModelScope)

    fun requestTranslation(text: String) {
        originalText.value = text
    }

    fun toggleSave() {
        viewModelScope.launch {
            translatedText.first()?.let {
                if (isExistsSavedTranslate.first())
                    deleteSavedTranslateUseCase(it)
                else
                    addSavedTranslateUseCase(originalText.first(), it)
            }
        }
    }
}