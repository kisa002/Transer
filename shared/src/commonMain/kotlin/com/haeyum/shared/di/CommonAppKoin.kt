package com.haeyum.shared.di

import org.koin.core.context.startKoin

object CommonAppKoin {
    fun startKoin() = startKoin {
        modules(commonApiModule, commonDataModule)
    }
}

