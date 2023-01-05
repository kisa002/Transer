package com.haeyum.common.presentation.preferences

import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import com.haeyum.common.domain.usecase.SetPreferencesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val coroutineScope: CoroutineScope,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase
) {
    val supportedLanguages = flow {
        emit(getSupportedLanguagesUseCase(target = "en", key = ""))
    }.catch {

    }.stateIn(scope = coroutineScope, started = SharingStarted.Lazily, initialValue = emptyList())

    val preferences = channelFlow {
        getPreferencesUseCase().collectLatest { preferences ->
            send(preferences)
        }
    }.shareIn(coroutineScope, SharingStarted.Eagerly, 1)

    val selectedSourceLanguage = preferences.filterNotNull().map { preferences ->
        preferences.sourceLanguage
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, null)

    val selectedTargetLanguage = preferences.filterNotNull().map { preferences ->
        preferences.targetLanguage
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, null)

    fun setSelectedSourceLanguage(language: Language) {
        coroutineScope.launch {
            selectedTargetLanguage.value?.let { targetLanguage ->
                setPreferencesUseCase(sourceLanguage = language, targetLanguage = targetLanguage)
            }
        }
    }

    fun setSelectedTargetLanguage(language: Language) {
        coroutineScope.launch {
            selectedSourceLanguage.value?.let { sourceLanguage ->
                setPreferencesUseCase(sourceLanguage = sourceLanguage, targetLanguage = language)
            }
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}