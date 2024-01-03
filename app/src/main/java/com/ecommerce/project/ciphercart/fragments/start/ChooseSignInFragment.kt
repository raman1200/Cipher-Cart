package com.ecommerce.project.ciphercart.fragments.start

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentChooseSignInBinding
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.goToMainActivity
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChooseSignInFragment : Fragment() {

    private lateinit var binding: FragmentChooseSignInBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    private val loginInViewModel : LogInViewModel by viewModels()

        override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChooseSignInBinding.inflate(layoutInflater, container, false)

        initialize()
        clickListeners()
        observer()

        return binding.root
    }

    private fun initialize() {
        googleSignInClient = loginInViewModel.getGoogleSignInClient(requireActivity())
    }

    private fun observer() {
        loginInViewModel.googleLogin.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.progressBar.visibility = View.GONE
                    goToMainActivity(requireActivity())
                }
                is Response.Error -> {
                    binding.progressBar.visibility = View.GONE
                    toast(requireContext(), it.message!!)
                }
            }
        }
    }

    private fun clickListeners() {
        binding.apply {
            signUp.setOnClickListener {
                findNavController().navigate(R.id.action_chooseSignInFragment_to_signUpFragment)
            }
            signInPassword.setOnClickListener {
                findNavController().navigate(R.id.action_chooseSignInFragment_to_logInFragment)
            }
            google1.setOnClickListener {
                progressBar.visibility = View.VISIBLE
                signInWithGoogle()
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
                val account:GoogleSignInAccount? = task.result
                if(account!=null){
                    loginInViewModel.signInWithGoogle(account.idToken!!)
                }

            }else{
                Toast.makeText(requireActivity(), task.exception.toString(), Toast.LENGTH_SHORT).show()
            }

        }

    }


}