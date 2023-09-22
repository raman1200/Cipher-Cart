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
import com.ecommerce.project.ciphercart.databinding.FragmentLogInBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class LogInFragment : Fragment() {
    private lateinit var binding:FragmentLogInBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)


        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        clickListeners()
        focusListeners()

        return binding.root
    }
    private fun initialize() {
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

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
                val email = emailEditText.text.toString()
                val password = passwordEditText.text.toString()
                if(email.isEmpty()){
                    emailTextInputLayout.error = "Please enter your email"
                }
                else if(password.isEmpty()){
                    passwordTextInputLayout.error = "Please enter your password"
                }
//                else{
//                    findNavController().navigate(R.id.action_logInFragment_to_mainActivity)
//                }
            }

            forgetPassword.setOnClickListener {
                editor.putInt("value", 2)
                editor.apply()
                val action = LogInFragmentDirections.actionLogInFragmentToForgotPasswordFragment(UserData())
                findNavController().navigate(action)

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