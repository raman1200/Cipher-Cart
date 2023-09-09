package com.ecommerce.project.ciphercart.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentLogInBinding

class LogInFragment : Fragment() {
    private lateinit var binding:FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)



        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.signUp.setOnClickListener{
            findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
        }
    }

}