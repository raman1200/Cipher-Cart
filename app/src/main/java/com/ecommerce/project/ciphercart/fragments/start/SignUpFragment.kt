package com.ecommerce.project.ciphercart.fragments.start

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentSignUpBinding
import com.ecommerce.project.ciphercart.dataclasses.User
import com.ecommerce.project.ciphercart.fragments.start.ForgotPasswordFragmentDirections.Companion.actionForgotPasswordFragmentToPinVerifyFragment
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
                val user = User(name, email, mobile, password)


                editor.putInt("value", 1)
                editor.apply()
                val action = SignUpFragmentDirections.actionSignUpFragmentToForgotPasswordFragment(User())
                findNavController().navigate(action)
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