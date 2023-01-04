package com.haeyum.common.presentation

import com.haeyum.common.domain.usecase.GetSupportedLanguagesUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class PreferencesViewModel(ioScope: CoroutineScope, languagesUseCase: GetSupportedLanguagesUseCase) {
    val languages = flow {
        emit(languagesUseCase(target = "en", key = ""))
    }.map {
        it.map {
            it.name
        }
    }.catch {
        emit(emptyList())
    }.stateIn(scope = ioScope, started = SharingStarted.Eagerly, emptyList())

    private val _selectedNativeLanguage = MutableStateFlow("")
    val selectedNativeLanguage: StateFlow<String> = _selectedNativeLanguage

    private val _selectedTargetLanguage = MutableStateFlow("")
    val selectedTargetLanguage: StateFlow<String> = _selectedTargetLanguage

    fun setSelectedNativeLanguage(language: String) {
        _selectedNativeLanguage.value = language
    }

    fun setSelectedTargetLanguage(language: String) {
        _selectedTargetLanguage.value = language
    }
}