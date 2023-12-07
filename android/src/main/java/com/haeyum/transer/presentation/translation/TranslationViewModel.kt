package com.haeyum.transer.presentation.translation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.shared.domain.usecase.recent.DeleteAndAddRecentTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.AddSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.DeleteSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.saved.GetSavedTranslatesUseCase
import com.haeyum.shared.domain.usecase.saved.IsExistsSavedTranslateUseCase
import com.haeyum.shared.domain.usecase.translation.TranslateUseCase
import com.haeyum.shared.extensions.decodeHtmlEntities
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
    private val deleteAndAddRecentTranslateUseCase: DeleteAndAddRecentTranslateUseCase
) : ViewModel() {
    private val _screenState = MutableStateFlow<TranslationScreenState>(TranslationScreenState.Translating)
    val screenState = _screenState.asStateFlow()

    private val originalText = MutableStateFlow("")
    private val translatedText = originalText
        .filter { it.isNotEmpty() }
        .map {
            runCatching {
                translateUseCase(it).translatedText.decodeHtmlEntities()
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
            deleteAndAddRecentTranslateUseCase(originalText, translatedText)
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