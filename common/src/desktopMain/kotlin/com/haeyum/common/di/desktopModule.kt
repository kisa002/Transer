package com.haeyum.common.di

import com.haeyum.common.presentation.DesktopViewModel
import com.haeyum.common.presentation.PreferencesViewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val desktopModule = module {
    factory {
        DesktopViewModel(
            get(named("IOScope")),
            get(),
            get()
        )
    }

    factory {
        PreferencesViewModel(
            get(named("IOScope")),
            get()
        )
    }
}