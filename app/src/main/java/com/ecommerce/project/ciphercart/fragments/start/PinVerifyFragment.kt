package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentPinVerifyBinding

class PinVerifyFragment : Fragment() {

    private lateinit var binding:FragmentPinVerifyBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPinVerifyBinding.inflate(layoutInflater, container, false)

        clickListeners()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            verifyBtn.setOnClickListener {
                findNavController().navigate(R.id.action_pinVerifyFragment_to_fingerPrintFragment)
            }
        }
    }

}