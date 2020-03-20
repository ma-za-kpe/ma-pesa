package com.maku.easydata.utils

import android.graphics.PorterDuff
import android.view.MotionEvent
import android.view.View

object MapesaUtils {

    fun buttonEffect(button: View) {
        button.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    v.background.setColorFilter(-0x1f0b8adf, PorterDuff.Mode.SRC_ATOP)
                    v.invalidate()
                }
                MotionEvent.ACTION_UP -> {
                    v.background.clearColorFilter()
                    v.invalidate()
                }
            }
            false
        }
    }



//    fun makeLightSaber(powers: Int): LightSaber {
//        return LightSaber(powers)
//    }
}