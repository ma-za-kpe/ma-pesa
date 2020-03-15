package com.maku.easydata.ui.secondTen

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
import com.google.gson.Gson
import com.maku.easydata.EasyDataApplication

import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentTenElevenBinding
import com.maku.easydata.databinding.FragmentTenNineBinding
import com.maku.easydata.model.SendAirtime
import com.maku.easydata.network.MyApiClient
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class TenElevenFragment : Fragment(), RewardedVideoAdListener {

    private lateinit var mRewardedVideoAd: RewardedVideoAd

    private lateinit var binding: FragmentTenElevenBinding

    private lateinit var navController: NavController

    private lateinit var number : String

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
        //in-app updates
        // Checks that the platform will allow the specified type of update.appUpdate()
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_ten_eleven, container, false)
        val sharedPref = activity?.getSharedPreferences("mapesa",Context.MODE_PRIVATE)
        number = sharedPref?.getString(getString(R.string.saved_phone_number), null).toString()


        Timber.d("number is.... " + number)


        binding.progressBar.visibility = View.GONE

        // rewarded ads
        MobileAds.initialize(activity, "ca-app-pub-1222362664019591~8722623706")
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(activity)
        mRewardedVideoAd.rewardedVideoAdListener = this

        // videos button
        binding.play.setOnClickListener { view ->
            loadRewardedVideoAd()
        }

        val mystring = resources.getString(R.string.videos_to_g_ten_one);

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


        return binding.root
    }

    private fun loadRewardedVideoAd() {

        //live ca-app-pub-1222362664019591/6083724058
        //dev ca-app-pub-3940256099942544/5224354917

        if (!(::mRewardedVideoAd.isInitialized) || !mRewardedVideoAd.isLoaded) {
            binding.progressBar.setVisibility(View.VISIBLE)
            mRewardedVideoAd.loadAd("ca-app-pub-1222362664019591/6083724058",
                    AdRequest.Builder().build())

        }
    }

    override fun onRewarded(reward: RewardItem) {
        Timber.d("person has been rewarded ...")
        // Reward the user.
        sendAirtime()
        Toast.makeText(activity, "Congratulations, you have received 100shs",
                Toast.LENGTH_SHORT).show()
        // Reward the user // move to next activity
        navController.navigate(R.id.horrayTenFragment)

    }

    override fun onRewardedVideoAdLeftApplication() {
//        Toast.makeText(activity, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdClosed() {
//        Toast.makeText(activity, "onRewardedVideoAdClosed", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoAdFailedToLoad(errorCode: Int) {
        Toast.makeText(activity, "PLEASE CHECK YOUR INTERNET CONNECTION", Toast.LENGTH_LONG).show()
        binding.progressBar.visibility = View.GONE
    }

    override fun onRewardedVideoAdLoaded() {
        // Toast.makeText(activity, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show()
        binding.progressBar.visibility =  View.GONE
        mRewardedVideoAd.show()
    }

    override fun onRewardedVideoAdOpened() {
//        Toast.makeText(activity, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoStarted() {
//        Toast.makeText(activity, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show()
    }

    override fun onRewardedVideoCompleted() {
//        Toast.makeText(activity, "onRewardedVideoCompleted", Toast.LENGTH_SHORT).show()
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

    /*start send airtime region*/
    private fun sendAirtime(){
        try {

            //phone number
            Timber.d("number is " + number)

            val list =  ArrayList<MutableMap<String, String>>()
            val Recipient:MutableMap<String,String> = mutableMapOf()
            Recipient["phoneNumber"] = number
            Recipient["amount"] = "UGX 100"
            list.add(Recipient)

            val json = Gson().toJson(list)

            Timber.d("example ..." + json)

            MyApiClient().doAtSending("3ad6c981292c48f6af8db491af1fc0de34a8873a67afba9864e0a9ffc1df9ab4","easyAirtime", json)?.enqueue(object : Callback<SendAirtime?> {

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

}
