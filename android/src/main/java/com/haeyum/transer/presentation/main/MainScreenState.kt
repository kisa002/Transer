package com.haeyum.transer.presentation.main

sealed class MainScreenState {
    object Recent : MainScreenState()
    object Saved : MainScreenState()
    object Preferences : MainScreenState()
}