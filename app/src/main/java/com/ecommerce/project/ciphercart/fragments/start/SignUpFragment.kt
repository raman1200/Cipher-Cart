package com.ecommerce.project.ciphercart.fragments.start

import android.app.Dialog
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
import com.ecommerce.project.ciphercart.databinding.FragmentSignUpBinding
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SHARED_PREFERENCES_NAME
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.showLoadingDialog
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer
import java.util.TimerTask

@AndroidEntryPoint
class SignUpFragment : Fragment() {
    lateinit var binding:FragmentSignUpBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor:Editor
    private lateinit var firebaseDb: FirebaseDb
    lateinit var userData: UserData
    private lateinit var dialog:Dialog

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
        observe()
        timer()


        return binding.root
    }

    // email verification checks in every 5 sec
    private fun timer() {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(registerViewModel.checkEmailVerify() == true){
                    userData.emailVerified = true
                    // save user data in firebase
                    if(userData.uid.isNotEmpty()){
                        registerViewModel.saveUserData(userData)
                    }
                    Log.d("TAG", "Verified")
                    timer.cancel()
                }
                else{
                    Log.d("TAG", "Not Verified ${registerViewModel.checkEmailVerify()}")
                }

            }
        }, 0, 5000)
    }

    private fun observe() {
        registerViewModel.register.observe(requireActivity()) {
            when (it) {
                is Response.Loading -> {
                    toast(requireContext(), "Loading")
                }

                is Response.Success -> {
                    toast(requireContext(), "Success")
                    dialog.dismiss()
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
                    registerViewModel.checkUserMobile(mobile){error, value ->
                        if(error!=null){
                            toast(requireContext(), error)     //  error message
                        }
                        else{
                            // check mobile number is already registered or not
                            if(value==true){
                                numberTextInputLayout.helperText = "Mobile Number is already Register"
                            }
                            else{
                                dialog = showLoadingDialog(requireContext())
                                userData.name = name
                                userData.email = email
                                userData.password = password
                                userData.number = mobile
                                editor.putInt("value", 1)
                                editor.apply()
                                registerViewModel.registerUser(userData)
                            }
                        }
                    }

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