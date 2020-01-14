package com.maku.easydata.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.databinding.DataBindingUtil
import com.maku.easydata.R
import com.maku.easydata.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    //glue between the layout and its views (binding variable)
    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_TIME_OUT:Long=3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler().postDelayed({

            startActivity(Intent(this, LoginActivity::class.java))

            finish()

        }, SPLASH_TIME_OUT)
    }
}
