package com.haeyum.common.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

object DesktopKoin {
    fun startKoin() = startKoin {
        modules(commonApiModule, commonDataModule, coroutineScopesModule, desktopModule)
    }
}