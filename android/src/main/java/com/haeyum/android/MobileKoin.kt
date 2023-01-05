package com.haeyum.android

import com.haeyum.common.di.commonApiModule
import com.haeyum.common.di.commonDataModule
import com.haeyum.common.di.coroutineScopesModule

object MobileKoin {
    fun startKoin() = org.koin.core.context.startKoin {
        modules(commonApiModule, commonDataModule, coroutineScopesModule, mobileModule)
    }
}