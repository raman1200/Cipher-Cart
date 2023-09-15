package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentChooseSignInBinding


class ChooseSignInFragment : Fragment() {

    private lateinit var binding: FragmentChooseSignInBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSignInBinding.inflate(layoutInflater, container, false)

        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_chooseSignInFragment_to_signUpFragment)
            }
            signInPassword.setOnClickListener {
                findNavController().navigate(R.id.action_chooseSignInFragment_to_logInFragment)
            }
        }
    }

}