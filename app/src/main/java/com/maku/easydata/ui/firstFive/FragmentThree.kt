package com.maku.easydata.ui.firstFive


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import com.maku.easydata.EasyDataApplication

import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentFragmentThreeBinding
import com.maku.easydata.databinding.FragmentFragmentTwoBinding
import com.maku.easydata.model.SendAirtime
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
class FragmentThree : Fragment(), RewardedVideoAdListener {


    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private lateinit var binding: FragmentFragmentThreeBinding

    private lateinit var navController: NavController

    val mContext: Context =
            EasyDataApplication.applicationContext()

    private val REQUEST_CODE = 20154

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_fragment_three, container, false)


        binding.progressBar.visibility = View.GONE
        binding.next.visibility = View.GONE
        binding.next.visibility = View.GONE

        // rewarded ads
        MobileAds.initialize(activity, "ca-app-pub-1222362664019591~8722623706")
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this

        // videos button
        binding.video.setOnClickListener { view ->
            loadRewardedVideoAd()
        }

        // next button
        binding.next.setOnClickListener { view ->
            navController.navigate(R.id.adsFragment)
        }


        return  binding.root
    }

    /* start rewarded ads*/

    private fun loadRewardedVideoAd() {

        if (!(::mRewardedVideoAd.isInitialized) || !mRewardedVideoAd.isLoaded) {
            binding.progressBar.setVisibility(View.VISIBLE)
            mRewardedVideoAd.loadAd("ca-app-pub-1222362664019591/6083724058",
                    AdRequest.Builder().build())

        }
    }

    override fun onRewarded(reward: RewardItem) {
        Timber.d("person has been rewarded ...")

        Toast.makeText(activity, "1 more videos to go",
                Toast.LENGTH_SHORT).show()
        // Reward the user.
        binding.next.visibility = View.VISIBLE

    }

    override fun onRewardedVideoAdLeftApplication() {
        //Toast.makeText(activity, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
       // Toast.makeText(activity, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {

        Toast.makeText(activity, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
        binding.goback.visibility = View.GONE

    }

    override fun onRewardedVideoAdLoaded() {
        //Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
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
