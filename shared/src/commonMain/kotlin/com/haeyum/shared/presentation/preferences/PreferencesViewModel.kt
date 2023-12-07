package com.haeyum.shared.presentation.preferences

import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.usecase.ClearDataUseCase
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.domain.usecase.translation.GetSupportedLanguagesUseCase
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class PreferencesViewModel(
    private val getSupportedLanguagesUseCase: GetSupportedLanguagesUseCase,
    private val getPreferencesUseCase: GetPreferencesUseCase,
    private val setPreferencesUseCase: SetPreferencesUseCase,
    private val clearDataUseCase: ClearDataUseCase
) : BaseViewModel() {
    val supportedLanguages = flow {
        emit(getSupportedLanguagesUseCase(target = "en"))
    }.catch {

    }.stateIn(scope = viewModelScope, started = SharingStarted.Lazily, initialValue = emptyList())

    val preferences = getPreferencesUseCase().shareIn(viewModelScope, SharingStarted.Eagerly, 1)

    val selectedSourceLanguage = preferences.filterNotNull().map { preferences ->
        preferences.sourceLanguage
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, null)

    val selectedTargetLanguage = preferences.filterNotNull().map { preferences ->
        preferences.targetLanguage
    }.stateIn(scope = viewModelScope, started = SharingStarted.Eagerly, null)

    fun setSelectedSourceLanguage(language: Language) {
        viewModelScope.launch {
            selectedTargetLanguage.value?.let { targetLanguage ->
                setPreferencesUseCase(sourceLanguage = language, targetLanguage = targetLanguage)
            }
        }
    }

    fun setSelectedTargetLanguage(language: Language) {
        viewModelScope.launch {
            selectedSourceLanguage.value?.let { sourceLanguage ->
                setPreferencesUseCase(sourceLanguage = sourceLanguage, targetLanguage = language)
            }
        }
    }

    fun clearData() {
        viewModelScope.launch {
            clearDataUseCase()
        }
    }
}