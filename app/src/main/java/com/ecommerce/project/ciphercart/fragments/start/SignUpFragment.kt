package com.ecommerce.project.ciphercart.fragments.start

import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.databinding.FragmentSignUpBinding
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.RegisterViewModel
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor:Editor
    lateinit var firebaseDb: FirebaseDb
    lateinit var userData: UserData


    private val registerViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignUpBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        clickListeners()
        focusListeners()

        registerViewModel.register.observe(requireActivity(), Observer {
            when(it){
                is Response.Loading -> { toast(requireContext(), "Loading")
                }
                is Response.Success -> { toast(requireContext(), "Success")
                }
                is Response.Error -> {toast(requireContext(), it.message!!)}
            }
        })
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {

                Log.d("TAG", "Running")
            }
        }, 0, 5000)

        return binding.root
    }

    private fun initialize() {
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
        firebaseDb = FirebaseDb()
        userData = UserData()
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
                    userData.name = name
                    userData.email = email
                    userData.password = password
                    userData.number = mobile
                    editor.putInt("value", 1)
                    editor.apply()
                    registerViewModel.registerUser(userData)
//                    val action = SignUpFragmentDirections.actionSignUpFragmentToForgotPasswordFragment(user)
//                    findNavController().navigate(action)

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