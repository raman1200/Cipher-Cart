package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentForgotPasswordBinding

class ForgotPasswordFragment : Fragment() {

    private lateinit var binding:FragmentForgotPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)


        clickListeners()

        // Inflate the layout for this fragment
        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            continueBtn.setOnClickListener {

                findNavController().navigate(R.id.action_forgotPasswordFragment_to_pinVerifyFragment)
            }
        }
    }

}