package com.maku.easydata.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType.FLEXIBLE
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.auth.FirebaseAuth
import com.maku.easydata.EasyDataApplication
import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentLoginActivityBinding
import timber.log.Timber


/**
 * A placeholder fragment containing a simple view.
 */
class LoginActivityFragment : Fragment() {

    //shared preferences
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "pesa"


    companion object {
        const val SIGN_IN_RESULT_CODE = 1001
    }

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()

    private lateinit var binding: FragmentLoginActivityBinding

    private lateinit var navController: NavController

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_activity, container, false)

        // TODO Remove the two lines below once observeAuthenticationState is implemented.
        binding.authButton.text = getString(R.string.login_btn)

        observeAuthenticationState()

        binding.authButton.setOnClickListener { launchSignInFlow() }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
    }

    private fun launchSignInFlow() {
        // Give users the option to sign in / register with their email or Google account.
        // If users choose to register with their email,
        // they will need to create a password as well.
        val providers = arrayListOf(
                AuthUI.IdpConfig.PhoneBuilder().build()

                // This is where you can provide more ways for users to register and
                // sign in.
        )

        // Create and launch sign-in intent.
        // We listen to the response of this activity with the
        // SIGN_IN_REQUEST_CODE
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                LoginActivityFragment.SIGN_IN_RESULT_CODE
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LoginActivityFragment.SIGN_IN_RESULT_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in
                Timber.d("Successfully signed in user ${FirebaseAuth.getInstance().currentUser?.phoneNumber}!")

                val sharedPref = activity?.getSharedPreferences("mapesa", Context.MODE_PRIVATE) ?: return
                with (sharedPref.edit()) {
                    putString(getString(R.string.saved_phone_number), FirebaseAuth.getInstance().currentUser?.phoneNumber)
                    apply()
                }
                navController.navigate(R.id.mainFragment)
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                Timber.d( "Sign in unsuccessful ${response?.error?.errorCode}")
            }
        }
    }

    private fun observeAuthenticationState() {

        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            // TODO 1. Use the authenticationState variable you just added
            // in LoginViewModel and change the UI accordingly.
            when (authenticationState) {
                // TODO 2.  If the user is logged in,
                // you can customize the welcome message they see by
                // utilizing the getFactWithPersonalization() function provided
                LoginViewModel.AuthenticationState.AUTHENTICATED -> navController.navigate(R.id.mainFragment)
                else -> {
                    // TODO 3. Lastly, if there is no logged-in user,
                    // auth_button should display Login and
                    // launch the sign in screen when clicked.

                    binding.authButton.text = getString(R.string.login_button_text)
                    binding.authButton.setOnClickListener {
                        launchSignInFlow()
                    }
                }
            }
        })
    }

}
