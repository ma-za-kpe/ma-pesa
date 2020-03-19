package com.maku.easydata.ui.firstFive

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.firebase.ui.auth.AuthUI
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.reward.RewardItem
import com.google.android.gms.ads.reward.RewardedVideoAd
import com.google.android.gms.ads.reward.RewardedVideoAdListener
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.maku.easydata.BuildConfig
import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentAdsBinding
import com.maku.easydata.model.AirtimerRecipient
import com.maku.easydata.model.SendAirtime
import com.maku.easydata.network.MyApi
import com.maku.easydata.network.MyApiClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import java.util.*
import kotlin.collections.ArrayList

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AdsFragment : Fragment(), RewardedVideoAdListener {

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private lateinit var binding: FragmentAdsBinding

    private lateinit var navController: NavController

    private lateinit var number : String

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
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_ads, container, false)

        //onback pressed

        val sharedPref = activity?.getSharedPreferences("mapesa",Context.MODE_PRIVATE)
         number = sharedPref?.getString(getString(R.string.saved_phone_number), null).toString()


        Timber.d("number is.... " + number)


        binding.progressBar.visibility = View.GONE

        // rewarded ads
        MobileAds.initialize(activity,BuildConfig.APP_ID)
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this

        // videos button
        binding.play.setOnClickListener { view ->
            loadRewardedVideoAd()
        }

        // start again button
//        binding.button7.setOnClickListener { view ->
//            navController.navigate(R.id.mainFragment)
//            /*start timer region, send time info to the main fragment*/
//
//            /*end timer region*/
//        }

        //logout
//        binding.logout.setOnClickListener { view ->
//            //material dialog
//            MaterialDialog(requireContext()).show {
//                title(R.string.your_title)
//                message(R.string.your_message)
//                positiveButton(R.string.yes){ dialog ->
//                    AuthUI.getInstance().signOut(requireContext())
//                    view.findNavController().navigate(R.id.action_adsFragment_to_loginActivityFragment)
//                    Toast.makeText(requireContext(), "Bye ...", Toast.LENGTH_SHORT).show()
//                }
//                negativeButton(R.string.no){ dialog ->
//                    Toast.makeText(requireContext(), "Keep going strong ...", Toast.LENGTH_SHORT).show()
//                }
//            }
//        }

        val mystring = resources.getString(R.string.videos_to_g_one);

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

    private fun loadRewardedVideoAd() {

        //live BuildConfig.EASY_MONEY
        //dev BuildConfig.APP_ID

        if (!(::mRewardedVideoAd.isInitialized) || !mRewardedVideoAd.isLoaded) {
            binding.progressBar.setVisibility(View.VISIBLE)
            mRewardedVideoAd.loadAd(BuildConfig.TESTING_AD_UNIT,
                    AdRequest.Builder().build())

        }
    }

    override fun onRewarded(reward: RewardItem) {
        Timber.d("You have received airtime, check")
        // Reward the user.
        sendAirtime()
        Toast.makeText(activity, "Congratulations, you have received 50shs",
                Toast.LENGTH_SHORT).show()

        //move to hooray activity
        navController.navigate(R.id.hoorayFragment)
    }

    override fun onRewardedVideoAdLeftApplication() {
       // Toast.makeText(activity, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
        //Toast.makeText(activity, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        Toast.makeText(activity, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility = View.GONE
    }

    override fun onRewardedVideoAdLoaded() {
       // Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility =  View.GONE
        mRewardedVideoAd.show()
    }

    override fun onRewardedVideoAdOpened() {
       // Toast.makeText(activity, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
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

    override fun onResume() {
        super.onResume()
        mRewardedVideoAd.resume(activity)
    }

    override fun onDestroy() {
        super.onDestroy()
        mRewardedVideoAd.destroy(activity)
    }

    /*start send airtime region*/
    private fun sendAirtime(){
            try {

                //phone number
                Timber.d("number is " + number)

                val list =  ArrayList<MutableMap<String, String>>()
                val Recipient:MutableMap<String,String> = mutableMapOf()
                Recipient["phoneNumber"] = number
                Recipient["amount"] = "UGX 50"
                list.add(Recipient)

                val json = Gson().toJson(list)

//                Timber.d("example ..." + json)
                Timber.d("example key ..." + BuildConfig.API_KEY)
                Timber.d("example key ..." + BuildConfig.USERNAME)
                Timber.d("example key ..." + BuildConfig.AD_9)

                MyApiClient().doAtSending(BuildConfig.API_KEY, BuildConfig.USERNAME, json)?.enqueue(object : Callback<SendAirtime?> {

                    override fun onFailure(call: Call<SendAirtime?>, t: Throwable) {

                        Timber.d("send airtime onFailure throwable " + t.message)

                    }

                    override fun onResponse(call: Call<SendAirtime?>, response: Response<SendAirtime?>) {
                        Timber.d("this is the one that run 1 ...")
                        if (response.isSuccessful){
                            Timber.d("this is the one that run 2 ...")
                            response.body()

                        } else {
                            Timber.d("this is the one that run 3...")

                                    try {
//                                     val jObjError = JSONObject(response.errorBody()?.string())
                                    Toast.makeText(context, response.errorBody()?.string(), Toast.LENGTH_LONG).show()
                                        Timber.d("send at " + response.errorBody())
                                    } catch (e : Exception) {
                                        Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
                                        Timber.d("send error"  + e.message)
                                    }

                        }
                    }
                })

            } catch (e: JSONException){
                e.printStackTrace()
            }
        }
    /*end send airtime region*/

    //on back pressed

}
