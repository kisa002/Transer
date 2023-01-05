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

    val selectedNativeLanguage = preferences.filterNotNull().map { preferences ->
        preferences.nativeLanguage
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, null)

    val selectedTargetLanguage = preferences.filterNotNull().map { preferences ->
        preferences.targetLanguage
    }.stateIn(scope = coroutineScope, started = SharingStarted.Eagerly, null)

    fun setSelectedNativeLanguage(language: Language) {
        coroutineScope.launch {
            selectedTargetLanguage.value?.let { targetLanguage ->
                setPreferencesUseCase(nativeLanguage = language, targetLanguage = targetLanguage)
            }
        }
    }

    fun setSelectedTargetLanguage(language: Language) {
        coroutineScope.launch {
            selectedNativeLanguage.value?.let { nativeLanguage ->
                setPreferencesUseCase(nativeLanguage = nativeLanguage, targetLanguage = language)
            }
        }
    }

    fun onDestroy() {
        coroutineScope.cancel()
    }
}