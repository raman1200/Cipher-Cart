package com.ecommerce.project.ciphercart.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)
        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            nameEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    nameTextInputLayout.hint = "" // Set an empty string to remove the label
                } else {
                    nameTextInputLayout.hint = "Name" // Restore the label when not focused
                }
            }
            numberEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    numberTextInputLayout.hint = "" // Set an empty string to remove the label
                } else {
                    numberTextInputLayout.hint = "Mobile" // Restore the label when not focused
                }
            }
            emailEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    emailTextInputLayout.hint = "" // Set an empty string to remove the label
                } else {
                    emailTextInputLayout.hint = "Email" // Restore the label when not focused
                }
            }
            passwordEditText.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    passwordTextInputLayout.hint = "" // Set an empty string to remove the label
                } else {
                    passwordTextInputLayout.hint = "Password" // Restore the label when not focused
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