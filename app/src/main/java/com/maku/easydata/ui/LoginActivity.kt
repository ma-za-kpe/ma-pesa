package com.maku.easydata.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.maku.easydata.R
import com.maku.easydata.databinding.ActivityLoginBinding
import timber.log.Timber


class LoginActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    //glue between the layout and its views
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login )

        createNotificationChannel()

    }


    /*create notification*/
    @SuppressLint("StringFormatInvalid")
    private fun createNotificationChannel() {
        //retrieve current registration token
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Timber.w("getInstanceId failed "+ task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    val msg = getString(R.string.msg_token_fmt, token)
                    Timber.d("token message " + msg + " "+ token)
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
