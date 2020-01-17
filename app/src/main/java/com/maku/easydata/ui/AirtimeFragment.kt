package com.maku.easydata.ui

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.android.gms.ads.rewarded.RewardedAd

import com.maku.easydata.R
import com.maku.easydata.databinding.AirtimeFragmentBinding

class AirtimeFragment : Fragment(), RewardedVideoAdListener {

    companion object {
        fun newInstance() = AirtimeFragment()
    }

    private lateinit var viewModel: AirtimeViewModel

    private lateinit var binding: AirtimeFragmentBinding

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private var mIsLoading = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.airtime_fragment, container, false)

        // rewarded ads
        MobileAds.initialize(activity, "ca-app-pub-3940256099942544~3347511713")
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this

        binding.logout.setOnClickListener { view ->
            //material dialog
            MaterialDialog(requireContext()).show {
                title(R.string.your_title)
                message(R.string.your_message)
                positiveButton(R.string.yes){ dialog ->
                    AuthUI.getInstance().signOut(requireContext())
                    view.findNavController().navigate(R.id.action_airtimeFragment_to_loginActivityFragment)
                    Toast.makeText(requireContext(), "Bye ...", Toast.LENGTH_SHORT).show()
                }
                negativeButton(R.string.no){ dialog ->
                    Toast.makeText(requireContext(), "Keep going strong ...", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // videos button
        binding.video.setOnClickListener{view ->
            loadRewardedVideoAd()
        }

        return binding.root
    }

    private fun loadRewardedVideoAd() {

        if (!(::mRewardedVideoAd.isInitialized) || !mRewardedVideoAd.isLoaded) {
            mIsLoading = true

            mRewardedVideoAd.loadAd("ca-app-pub-3940256099942544/5224354917",
                    AdRequest.Builder().build())

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AirtimeViewModel::class.java)
        // TODO: Use the ViewModel
    }

    override fun onRewarded(reward: RewardItem) {

    Toast.makeText(activity, "onRewarded! currency: ${reward.type} amount: ${reward.amount}",
    Toast.LENGTH_SHORT).show()
    // Reward the user.
}

override fun onRewardedVideoAdLeftApplication() {
    Toast.makeText(activity, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoAdClosed() {
    Toast.makeText(activity, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
    Toast.makeText(activity, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoAdLoaded() {
    Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoAdOpened() {
    Toast.makeText(activity, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoStarted() {
    Toast.makeText(activity, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
}

override fun onRewardedVideoCompleted() {
    Toast.makeText(activity, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
}

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(activity)
    }

    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(activity)
    }

}
