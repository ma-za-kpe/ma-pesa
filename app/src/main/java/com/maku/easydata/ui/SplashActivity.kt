package com.maku.easydata.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.maku.easydata.EasyDataApplication
import com.maku.easydata.R
import com.maku.easydata.databinding.ActivitySplashBinding
import timber.log.Timber


class SplashActivity : AppCompatActivity() {

    //glue between the layout and its views (binding variable)
    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_TIME_OUT: Long = 3000

//    // Creates instance of the manager.
//    val appUpdateManager = AppUpdateManagerFactory.create(this)
//    // Returns an intent object that you use to check for an update.
//    val appUpdateInfoTask = appUpdateManager.appUpdateInfo
//    private val MY_REQUEST_CODE = 13751

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivitySplashBinding>(this, R.layout.activity_splash)

        Handler().postDelayed({

            startActivity(Intent(this, LoginActivity::class.java))

            finish()

        }, SPLASH_TIME_OUT)

    }
}