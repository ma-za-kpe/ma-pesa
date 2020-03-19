package com.maku.easydata.ui

import android.R.attr.endColor
import android.R.attr.startColor
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
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
import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentMainBinding


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

        //onclick
        // start again button
        binding.fiftyBtn.setOnClickListener { view ->
            navController.navigate(R.id.fragmentOne)
        }

        binding.onehundredBtn.setOnClickListener { view ->
            navController.navigate(R.id.tenOneFragment)
        }

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


}
