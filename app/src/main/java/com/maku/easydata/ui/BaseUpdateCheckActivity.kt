package com.maku.easydata.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.*
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.AppUpdateType.*
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.maku.easydata.EasyDataApplication
import com.maku.easydata.R
import timber.log.Timber

class BaseUpdateCheckActivity : AppCompatActivity() {

    val mContext: Context =
            EasyDataApplication.applicationContext()

    private val REQUEST_CODE = 20154

    private val appUpdateManager: AppUpdateManager by lazy { AppUpdateManagerFactory.create(this) }
    private val appUpdatedListener: InstallStateUpdatedListener by lazy {
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(installState: InstallState) {
                when {
                    installState.installStatus() == InstallStatus.DOWNLOADED -> popupSnackbarForCompleteUpdate()
                    installState.installStatus() == InstallStatus.INSTALLED -> appUpdateManager.unregisterListener(this)
                    else -> Timber.d("InstallStateUpdatedListener: state: %s", installState.installStatus())
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_update_check)
        checkForAppUpdate()
    }

    private fun checkForAppUpdate() {
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE) {
                // Request the update.
                try {
                    val installType = when {
                        appUpdateInfo.isUpdateTypeAllowed(FLEXIBLE) -> FLEXIBLE
                        appUpdateInfo.isUpdateTypeAllowed(IMMEDIATE) -> IMMEDIATE
                        else -> null
                    }
                    if (installType == FLEXIBLE) appUpdateManager.registerListener(appUpdatedListener)

                    appUpdateManager.startUpdateFlowForResult(
                            appUpdateInfo,
                            installType!!,
                            this,
                            REQUEST_CODE)
                } catch (e: IntentSender.SendIntentException) {
                    e.printStackTrace()
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this,
                        "App Update failed, please try again on the next app launch.",
                        Toast.LENGTH_SHORT)
                        .show()
            }
        }
    }

    private fun popupSnackbarForCompleteUpdate() {
        val snackbar = make(
                findViewById(R.id.snack),
                "An update has just been downloaded.",
                LENGTH_INDEFINITE)
        snackbar.setAction("RESTART") { appUpdateManager.completeUpdate() }
        snackbar.setActionTextColor(ContextCompat.getColor(this, R.color.snackbar_action_text_color))
        snackbar.show()
    }


    override fun onResume() {
        super.onResume()
        appUpdateManager
                .appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->

                    // If the update is downloaded but not installed,
                    // notify the user to complete the update.
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        popupSnackbarForCompleteUpdate()
                    }

                    //Check if Immediate update is required
                    try {
                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                            // If an in-app update is already running, resume the update.
                            appUpdateManager.startUpdateFlowForResult(
                                    appUpdateInfo,
                                    IMMEDIATE,
                                    this,
                                    REQUEST_CODE)
                        }
                    } catch (e: IntentSender.SendIntentException) {
                        e.printStackTrace()
                    }
                }
    }


}
