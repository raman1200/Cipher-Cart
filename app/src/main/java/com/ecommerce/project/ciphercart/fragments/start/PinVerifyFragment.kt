package com.ecommerce.project.ciphercart.fragments.start

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentPinVerifyBinding
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SHARED_PREFERENCES_NAME
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import javax.inject.Inject

class PinVerifyFragment : Fragment() {

    private lateinit var binding:FragmentPinVerifyBinding
    @Inject
    lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinVerifyBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        getData()
        clickListeners()


        return binding.root
    }
    private fun initialize() {
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    }
    private fun getData() {
        val value = sharedPreferences.getInt("verify_value", -1)
        setLayout(value)
    }

    private fun setLayout(value:Int){
        binding.apply {
            if(value==1){
                title.text = "Verify"
                codeLayout.visibility = View.VISIBLE
                resetLayout.visibility = View.VISIBLE
                pinTv.visibility = View.GONE

            }
            else if(value==2){
                title.text = "Create New Pin"
                codeLayout.visibility = View.GONE
                resetLayout.visibility = View.GONE
                pinTv.visibility = View.VISIBLE
            }
        }
    }

    private fun clickListeners() {
        binding.apply {
//            verifyBtn.setOnClickListener {
//                findNavController().navigate(R.id.action_pinVerifyFragment_to_mainActivity)

//            }
        }
    }

}