package com.haeyum.android.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haeyum.common.domain.model.translation.languages.Language
import com.haeyum.common.domain.usecase.preferences.GetPreferencesUseCase
import com.haeyum.common.domain.usecase.preferences.SetPreferencesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class MainViewModel(
    getPreferencesUseCase: GetPreferencesUseCase,
    setPreferencesUseCase: SetPreferencesUseCase
) : ViewModel() {
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