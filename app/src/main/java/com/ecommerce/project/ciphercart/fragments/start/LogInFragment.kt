package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentLogInBinding
import com.ecommerce.project.ciphercart.utils.etHintTextChange

class LogInFragment : Fragment() {
    private lateinit var binding:FragmentLogInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)



        clickListeners()
        focusListeners()

        return binding.root
    }

    private fun focusListeners() {
        binding.apply {
            etHintTextChange(emailEditText, emailTextInputLayout, "Email")
            etHintTextChange(passwordEditText, passwordTextInputLayout, "Password")
        }
    }

    private fun clickListeners() {

        binding.apply {
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_signUpFragment)
            }
            signIn.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_mainActivity)
            }
            forgetPassword.setOnClickListener {
                findNavController().navigate(R.id.action_logInFragment_to_forgotPasswordFragment)
            }


            layout1.setOnClickListener {
                if(it != emailTextInputLayout || it!=passwordTextInputLayout){
                    emailTextInputLayout.clearFocus()
                    passwordTextInputLayout.clearFocus()
                }
            }
        }
    }

}