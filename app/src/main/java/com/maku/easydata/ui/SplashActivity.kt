package com.maku.easydata.ui

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.maku.easydata.R
import com.maku.easydata.databinding.ActivitySplashBinding
import timber.log.Timber


class SplashActivity : AppCompatActivity() {

    //glue between the layout and its views (binding variable)
    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_TIME_OUT:Long=3000

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

        //in-app updates
        // Checks that the platform will allow the specified type of update.
//        appUpdate()

    }

//    private fun appUpdate() {
//        // Checks that the platform will allow the specified type of update.
//        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
//            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
//                    // For a flexible update, use AppUpdateType.FLEXIBLE
//                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
//            ) {
//              try{
//                  // Request the update.
//                  appUpdateManager.startUpdateFlowForResult(
//                          // Pass the intent that is returned by 'getAppUpdateInfo()'.
//                          appUpdateInfo,
//                          // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
//                          AppUpdateType.IMMEDIATE,
//                          // The current activity making the update request.
//                          this,
//                          // Include a request code to later monitor this update request.
//                          MY_REQUEST_CODE)
//              } catch (e: IntentSender.SendIntentException ) {
//                    e.printStackTrace()
//              }
//            } else if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED){
//                popupSnackbarForCompleteUpdate();
//            } else {
//                Timber.e( "checkForAppUpdateAvailability: something else");
//            }
//        }
//    }
//
//    // Get a callback for update status
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == MY_REQUEST_CODE) {
//            if (resultCode != RESULT_OK) {
//                Timber.d("Update flow failed! Result code: ${Activity.RESULT_OK}")
//                // If the update is cancelled or fails,
//                // you can request to start the update again.
//                Toast.makeText(this,
//                        "App Update failed, please try again on the next app launch.",
//                        Toast.LENGTH_SHORT)
//                        .show()
//            }
//        }
//
//    }
//
//    /* Displays the snackbar notification and call to action. */
//    fun popupSnackbarForCompleteUpdate() {
//        Snackbar.make(
//                findViewById(R.id.activity_main_layout),
//                "An update has just been downloaded.",
//                Snackbar.LENGTH_INDEFINITE
//        ).apply {
//            setAction("RESTART") { appUpdateManager.completeUpdate() }
//            setActionTextColor(resources.getColor(R.color.colorPrimary))
//            show()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        appUpdateManager
//                .appUpdateInfo
//                .addOnSuccessListener { appUpdateInfo ->
//
//                    // If the update is downloaded but not installed,
//                    // notify the user to complete the update.
//                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
//                        popupSnackbarForCompleteUpdate()
//                    }
//
//                    //Check if Immediate update is required
//                    try {
//                        if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
//                            // If an in-app update is already running, resume the update.
//                            appUpdateManager.startUpdateFlowForResult(
//                                    appUpdateInfo,
//                                    AppUpdateType.IMMEDIATE,
//                                    this,
//                                    Activity.RESULT_OK)
//                        }
//                    } catch (e: IntentSender.SendIntentException) {
//                        e.printStackTrace()
//                    }
//                }
//    }

}
