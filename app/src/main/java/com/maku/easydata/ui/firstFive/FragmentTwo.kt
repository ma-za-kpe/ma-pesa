package com.maku.easydata.ui.firstFive


import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maku.easydata.BuildConfig
import com.maku.easydata.EasyDataApplication

import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentFragmentTwoBinding
import com.maku.easydata.network.MyApi
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class FragmentTwo : Fragment(), RewardedVideoAdListener {

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private lateinit var binding: FragmentFragmentTwoBinding

    private lateinit var navController: NavController

    val mContext: Context =
            EasyDataApplication.applicationContext()

    private val REQUEST_CODE = 20154

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()

        requireActivity().onBackPressedDispatcher
                .addCallback(viewLifecycleOwner, object: OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() {
                        //Handle back event from any fragment
                        navController.popBackStack(R.id.mainFragment, false);

                    }
                })
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_two, container, false)


        binding.progressBar.visibility = View.GONE

        // rewarded ads
        MobileAds.initialize(activity, BuildConfig.APP_ID)
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this

        // videos button
        binding.play.setOnClickListener { view ->
            loadRewardedVideoAd()
        }

        val mystring = resources.getString(R.string.videos_to_g_fourr);

        val spannable = SpannableString(mystring);
        spannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.pink)),
                0, 3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                0, spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        binding.videos.text = spannable

        return  binding.root
    }

    /* start rewarded ads*/

    private fun loadRewardedVideoAd() {

        //live BuildConfig.AD_2
        //dev BuildConfig.TESTING_AD_UNIT

        if (!(::mRewardedVideoAd.isInitialized) || !mRewardedVideoAd.isLoaded) {
            binding.progressBar.setVisibility(View.VISIBLE)
            mRewardedVideoAd.loadAd(BuildConfig.TESTING_AD_UNIT,
                    AdRequest.Builder().build())

        }
    }

    override fun onRewarded(reward: RewardItem) {
        Timber.d("person has been rewarded ...")

        Toast.makeText(activity, "3 more videos togo",
                Toast.LENGTH_SHORT).show()
        // Reward the user.
        navController.navigate(R.id.fragmentThree)

    }

    override fun onRewardedVideoAdLeftApplication() {
//        Toast.makeText(activity, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
       // Toast.makeText(activity, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        Toast.makeText(activity, "PLEASE CHECK YOUR INTERNET CONNECTION", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    override fun onRewardedVideoAdLoaded() {
       // Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility =  View.GONE
        mRewardedVideoAd.show()
    }

    override fun onRewardedVideoAdOpened() {
        //Toast.makeText(activity, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
        //Toast.makeText(activity, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
        //Toast.makeText(activity, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
        binding.progressBar.setVisibility(View.GONE)
    }

    override fun onPause() {
        super.onPause()
        mRewardedVideoAd.pause(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(activity)
    }

    /*end rewarded ads*/


}
