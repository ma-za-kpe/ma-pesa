package com.maku.easydata

import android.R
import android.app.Application
import android.content.Context
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
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
        //timber for logging
        Timber.plant(Timber.DebugTree())

        //calligrapX for fonts
//        ViewPump.init(ViewPump.builder()
//                .addInterceptor(CalligraphyInterceptor(
//                        Builder()
//                                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
//                                .setFontAttrId(R.attr.fontPath)
//                                .build()))
//                .build())
    }
}