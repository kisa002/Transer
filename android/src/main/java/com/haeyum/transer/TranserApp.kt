package com.haeyum.transer

import android.app.Application
import com.haeyum.transer.di.MobileKoin

class TranserApp: Application() {
    override fun onCreate() {
        super.onCreate()
        MobileKoin.startKoin(context = this)
    }
}