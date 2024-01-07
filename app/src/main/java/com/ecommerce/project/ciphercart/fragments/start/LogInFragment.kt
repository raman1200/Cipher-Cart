package com.ecommerce.project.ciphercart.fragments.start

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.MainActivity
import com.ecommerce.project.ciphercart.databinding.FragmentLogInBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SHARED_PREFERENCES_NAME
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.etHintTextChange
import com.ecommerce.project.ciphercart.utils.goToMainActivity
import com.ecommerce.project.ciphercart.utils.hideKeyboard
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.firestore.auth.User
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class LogInFragment : Fragment() {
    private lateinit var binding:FragmentLogInBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    @Inject
    lateinit var userDataManager: UserDataManager

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
                    binding.progressBar.visibility = View.VISIBLE
                    toast(requireContext(), "loading")
                }

                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    userDataManager.saveUserData(it.data)
                    toast(requireContext(), "Success ")
                    goToMainActivity(requireActivity())
                }

                is Response.Error -> {
                    binding.progressBar.visibility = View.GONE
                    toast(requireContext(), it.message!!)
                }
            }
        }
        logInViewModel.googleLogin.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    userDataManager.saveUserData(it.data)
                    binding.progressBar.visibility = View.GONE
                    toast(requireContext(), "Success")
                    goToMainActivity(requireActivity())
                }
                is Response.Error -> {
                    binding.progressBar.visibility = View.GONE
                    toast(requireContext(), it.message!!)
                }
            }
        }
    }

    private fun initialize() {

        googleSignInClient = logInViewModel.getGoogleSignInClient(requireActivity())
    }

    private fun focusListeners() {
        binding.apply {
            etHintTextChange(emailEditText, emailTextInputLayout, "Email")
            etHintTextChange(passwordEditText, passwordTextInputLayout, "Password")
        }
    }

    private fun clickListeners() {
        binding.apply {
            skip.setOnClickListener {
                goToMainActivity(requireActivity())
            }
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
                    logInViewModel.logInbyUser(email, password)
                }
            }
            google1.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                signInWithGoogle()
            }
            forgetPassword.setOnClickListener {
//                editor.putInt("value", 2)
//                editor.apply()
//                val action = LogInFragmentDirections.action_logInFragment_to_resetPasswordFragment(UserData())
//                findNavController().navigate(action)
                findNavController().navigate(R.id.action_logInFragment_to_resetPasswordFragment)

            }


            layout1.setOnClickListener {
                if(it != emailTextInputLayout || it!=passwordTextInputLayout){
                    emailTextInputLayout.clearFocus()
                    passwordTextInputLayout.clearFocus()
                }
            }
        }
    }

    private fun signInWithGoogle() {
        googleSignInClient.signOut()
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)

    }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            if(task.isSuccessful){
                val account: GoogleSignInAccount? = task.result
                if(account!=null){
                    logInViewModel.signInWithGoogle(account.idToken!!)
                }

            }else{
                Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }

    }

    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.GONE
    }


}