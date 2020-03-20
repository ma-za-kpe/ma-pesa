package com.maku.easydata.ui

import android.R.attr.endColor
import android.R.attr.startColor
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.net.Uri
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentMainBinding
import com.thekhaeng.pushdownanim.PushDownAnim
import timber.log.Timber


class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    private lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = findNavController()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_main, container, false)

//        createDynamicLinks()

        //share app
        PushDownAnim.setPushDownAnimTo( binding.share )
                .setOnClickListener{ view ->
                    onShareClicked()
                }

        //onclick
        // start again button
        PushDownAnim.setPushDownAnimTo( binding.fiftyBtn )
                .setOnClickListener{ view ->
                    navController.navigate(R.id.fragmentOne)
                };

        PushDownAnim.setPushDownAnimTo(  binding.onehundredBtn )
                .setOnClickListener{ view ->
                    navController.navigate(R.id.tenOneFragment)
                };


        //build the spannable String for 50 shillings
        val mystring = resources.getString(R.string.watch_4_vid);

        val spannable = SpannableString(mystring);
        spannable.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.pink)),
                19, 33,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );
        spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                19, spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        binding.watchfour.text = spannable

        //build the spannable String for 100 shillings
        val nine = resources.getString(R.string.watch_9_vid);

        val one = SpannableString(nine);
        one.setSpan(
                ForegroundColorSpan(resources.getColor(R.color.pink)),
                19, 34,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        one.setSpan(
                StyleSpan(Typeface.BOLD),
                19, spannable.length,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        );

        binding.watchnine.text = one

        return binding.root
    }


        fun generateContentLink(): Uri {
            val baseUrl = Uri.parse("https://www.example.com/?curPage=1")
            val domain = "https://easydata.page.link/freeairtime"

            val link = FirebaseDynamicLinks.getInstance()
                    .createDynamicLink()
                    .setLink(baseUrl)
                    .setDomainUriPrefix(domain)
                    .setAndroidParameters(DynamicLink.AndroidParameters.Builder("com.your.easydata").build())
                    .buildDynamicLink()

            return link.uri
        }

    private fun onShareClicked() {
        val link = generateContentLink()

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, link.toString())

        startActivity(Intent.createChooser(intent, "Share Link"))
    }

//    private fun createDynamicLinks() {
//
////        Firebase.dynamicLinks
//        FirebaseDynamicLinks.getInstance()
//                .getDynamicLink(intent)
//                .addOnSuccessListener(this) { pendingDynamicLinkData ->
//                    // Get deep link from result (may be null if no link is found)
//                    var deepLink: Uri? = null
//                    if (pendingDynamicLinkData != null) {
//                        deepLink = pendingDynamicLinkData.link
//                    }
//
//                    // Handle the deep link. For example, open the linked
//                    // content, or apply promotional credit to the user's
//                    // account.
//                    // ...
//                    if (deepLink != null){
//                        Timber.d("deep link is " + deepLink.toString())
//                    }
//                    // ...
//                }
//                .addOnFailureListener(this) { e -> Timber.d( "getDynamicLink:onFailure" +  e) }
//
//    }


}
