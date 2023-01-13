package com.haeyum.android

import android.app.Application
import com.haeyum.android.di.MobileKoin

class TranserApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MobileKoin.startKoin(context = this)
    }
}