package com.ecommerce.project.ciphercart.fragments.start

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.databinding.FragmentSignUpBinding
import com.ecommerce.project.ciphercart.dataclasses.UserData
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:Editor
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

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
            etHintTextChange(nameEditText, nameTextInputLayout, "Name")
            etHintTextChange(emailEditText, emailTextInputLayout, "Email")
            etHintTextChange(numberEditText, numberTextInputLayout, "Number")
            etHintTextChange(passwordEditText, passwordTextInputLayout, "Password")
        }
    }

    private fun clickListeners() {
        binding.apply {
            signUp.setOnClickListener {

                val name = nameEditText.text.toString()
                val email = emailEditText.text.toString()
                val mobile = numberEditText.text.toString()
                val password = passwordEditText.text.toString()
                if (name.isEmpty()){
                    nameTextInputLayout.error= "Please Enter Your Name"
                }
                else if (email.isEmpty()){
                    emailTextInputLayout.error = "Please enter your email"
                }
                else if (mobile.length != 10){
                    numberTextInputLayout.error = "Please Enter Correct Number"
                }
                else if (password.isEmpty()){
                    passwordTextInputLayout.error = "Please Enter Password"
                }
                else {

                    val user = UserData(name = name, email = email, number = mobile, password = password)

                    editor.putInt("value", 1)
                    editor.apply()
                    val action = SignUpFragmentDirections.actionSignUpFragmentToForgotPasswordFragment(user)
                    findNavController().navigate(action)

                }


            }


            constraint.setOnClickListener {
                emailTextInputLayout.clearFocus()
                numberTextInputLayout.clearFocus()
                passwordTextInputLayout.clearFocus()
                nameTextInputLayout.clearFocus()
            }
        }
    }

}