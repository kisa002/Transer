package com.haeyum.shared.presentation.mobile

sealed class MainScreenState {
    data object Translate : MainScreenState()
    data object Recent : MainScreenState()
    data object Saved : MainScreenState()
    data object Preferences : MainScreenState()
}