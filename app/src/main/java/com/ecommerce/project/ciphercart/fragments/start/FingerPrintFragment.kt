package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentFingerPrintBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class FingerPrintFragment : Fragment() {

    lateinit var binding:FragmentFingerPrintBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFingerPrintBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        clickListeners()



        return binding.root
    }

    private fun clickListeners() {
        binding.apply {

            continueBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fingerPrintFragment_to_mainActivity)
            }
            skipBtn.setOnClickListener {
                findNavController().navigate(R.id.action_fingerPrintFragment_to_mainActivity)
            }
        }
    }

}