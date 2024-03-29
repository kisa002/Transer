package com.haeyum.transer.di

import android.content.Context
import com.haeyum.shared.di.commonApiModule
import com.haeyum.shared.di.commonDataModule
import com.haeyum.shared.di.coroutineScopesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

object MobileKoin {
    fun startKoin(context: Context) = org.koin.core.context.startKoin {
        androidLogger()
        androidContext(context)
        modules(commonApiModule, commonDataModule, coroutineScopesModule, mobileModule)
    }
}