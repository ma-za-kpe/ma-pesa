package com.maku.easydata

import android.app.Application
import timber.log.Timber

class EasyDataApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}