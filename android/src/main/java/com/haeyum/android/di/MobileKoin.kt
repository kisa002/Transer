package com.haeyum.android.di

import android.content.Context
import com.haeyum.common.di.commonApiModule
import com.haeyum.common.di.commonDataModule
import com.haeyum.common.di.coroutineScopesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

object MobileKoin {
    fun startKoin(context: Context) = org.koin.core.context.startKoin {
        androidLogger()
        androidContext(context)
        modules(commonApiModule, commonDataModule, coroutineScopesModule, mobileModule)
    }
}