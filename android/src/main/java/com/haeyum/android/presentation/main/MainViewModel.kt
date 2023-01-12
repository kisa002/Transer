package com.haeyum.android.presentation.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainViewModel : ViewModel() {
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
}