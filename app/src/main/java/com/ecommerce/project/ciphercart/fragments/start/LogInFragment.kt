package com.ecommerce.project.ciphercart.fragments.start

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.MainActivity
import com.ecommerce.project.ciphercart.databinding.FragmentLogInBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SHARED_PREFERENCES_NAME
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment() {
    private lateinit var binding:FragmentLogInBinding

    @Inject
    lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor


    private val logInViewModel:LogInViewModel by viewModels()




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogInBinding.inflate(layoutInflater, container, false)


        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        clickListeners()
        focusListeners()
        observe()

        return binding.root
    }

    private fun observe() {
        logInViewModel.logIn.observe(requireActivity()) {

            when (it) {
                is Response.Loading -> {
                    toast(requireContext(), "loading")
                }

                is Response.Success -> {
                    toast(requireContext(), "Success")
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                }

                is Response.Error -> {
                    toast(requireContext(), it.message!!)
                }
            }
        }

    }








    private fun initialize() {
        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
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
                else{
//                    findNavController().navigate(R.id.action_logInFragment_to_mainActivity)
                    logInViewModel.logInbyUser(email, password)

                }
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