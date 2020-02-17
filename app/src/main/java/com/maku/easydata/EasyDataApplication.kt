package com.maku.easydata

import android.app.Application
import android.content.Context
import timber.log.Timber

class EasyDataApplication  : Application() {

    //context
    init {
        instance = this
    }

    companion object {
        private var instance: EasyDataApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}