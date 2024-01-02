package com.haeyum.transer.presentation.main

import com.haeyum.shared.domain.model.translation.languages.Language
import com.haeyum.shared.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.shared.domain.usecase.preferences.SetPreferencesUseCase
import com.haeyum.shared.presentation.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    getPreferencesUseCase: GetPreferencesUseCase,
    setPreferencesUseCase: SetPreferencesUseCase
) : BaseViewModel() {
    private val _screenState = MutableStateFlow<MainScreenState>(MainScreenState.Recent)
    val screenState = _screenState.asStateFlow()

    fun navigateToRecent() {
        _screenState.value = MainScreenState.Recent
    }

    fun navigateToSaved() {
        _screenState.value = MainScreenState.Saved
    }

    fun navigateToPreferences() {
        _screenState.value = MainScreenState.Preferences
    }

    init {
        viewModelScope.launch {
            if (getPreferencesUseCase().firstOrNull() == null)
                setPreferencesUseCase(Language("en", "English"), Language("ko", "Korean"))
        }
    }
}