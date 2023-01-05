package com.haeyum.android

import android.app.Application
import com.haeyum.common.di.commonApiModule
import com.haeyum.common.di.commonDataModule
import com.haeyum.common.di.coroutineScopesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TranserApp: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TranserApp)
            modules(commonApiModule, commonDataModule, coroutineScopesModule, mobileModule)
        }
//        MobileKoin.startKoin()
    }
}