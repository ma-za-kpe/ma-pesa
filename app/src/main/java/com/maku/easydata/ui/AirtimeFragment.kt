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

import com.maku.easydata.R
import com.maku.easydata.databinding.AirtimeFragmentBinding

class AirtimeFragment : Fragment() {

    companion object {
        fun newInstance() = AirtimeFragment()
    }

    private lateinit var viewModel: AirtimeViewModel

    private lateinit var binding: AirtimeFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(
                inflater, R.layout.airtime_fragment, container, false)

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
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(AirtimeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
