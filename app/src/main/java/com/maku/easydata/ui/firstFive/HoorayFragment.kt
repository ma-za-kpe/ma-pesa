package com.maku.easydata.ui.firstFive

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

import com.maku.easydata.R
import com.maku.easydata.databinding.FragmentHoorayBinding
import com.thekhaeng.pushdownanim.PushDownAnim

/**
 * A simple [Fragment] subclass.
 */
class HoorayFragment : Fragment() {

    private lateinit var binding: FragmentHoorayBinding

    private lateinit var navController: NavController

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
                inflater, R.layout.fragment_hooray, container, false)

        PushDownAnim.setPushDownAnimTo(  binding.rectangle1 )
                .setOnClickListener{ view ->
                    navController.navigate(R.id.mainFragment)
                };

        PushDownAnim.setPushDownAnimTo(  binding.continueE )
                .setOnClickListener{ view ->
                    navController.navigate(R.id.fragmentOne)
                };

//        binding.rectangle1.setOnClickListener { navController.navigate(R.id.mainFragment) }
//
//        binding.continueE.setOnClickListener{
//            navController.navigate(R.id.fragmentOne)
//        }

         return  binding.root
    }

}
