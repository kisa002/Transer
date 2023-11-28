package com.haeyum.shared.di

import TranslateViewModel
import com.haeyum.shared.presentation.MainViewModel
import com.haeyum.shared.presentation.mobile.recent.RecentTranslateViewModel
import com.haeyum.shared.presentation.mobile.saved.SavedViewModel
import com.haeyum.shared.presentation.preferences.PreferencesViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

class DIHelper : KoinComponent {
    val mainViewModel
        get() = get<MainViewModel>()

    val recentTranslateViewModel
        get() = get<RecentTranslateViewModel>()

    val savedViewModel
        get() = get<SavedViewModel>()

    val preferencesViewModel
        get() = get<PreferencesViewModel>()

    val translateViewModel
        get() = get<TranslateViewModel>()
}