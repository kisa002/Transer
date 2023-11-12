package com.haeyum.shared.presentation.preferences

import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.usecase.ClearDataUseCase
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.translation.GetSupportedLanguagesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val coroutineScope: CoroutineScope,
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase,
    private val clearDataUseCase: ClearDataUseCase
) {
    val supportedLanguages = flow {
        emit(getSupportedLanguagesUseCase(target = "en"))
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

    fun clearData() {
        coroutineScope.launch {
            clearDataUseCase()
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}