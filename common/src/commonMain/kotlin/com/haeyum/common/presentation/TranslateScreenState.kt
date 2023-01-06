package com.haeyum.common.presentation

sealed class TranslateScreenState {
    object Home : TranslateScreenState()
    object Translate : TranslateScreenState()
    object Recent : TranslateScreenState()
    object Favorite : TranslateScreenState()
}