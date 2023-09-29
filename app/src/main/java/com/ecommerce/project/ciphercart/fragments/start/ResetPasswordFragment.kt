package com.ecommerce.project.ciphercart.fragments.start

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentForgotPasswordBinding
import com.ecommerce.project.ciphercart.databinding.FragmentResetPasswordBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.getDialog
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.ecommerce.project.ciphercart.viewmodels.RegisterViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext

@AndroidEntryPoint
class ResetPasswordFragment : Fragment() {

    private lateinit var binding: FragmentResetPasswordBinding

    private lateinit var dialog: Dialog
    private lateinit var resetLayout:LinearLayout
    private lateinit var verifyLayout:LinearLayout
    private lateinit var congratulationLayout:LinearLayout



    private val logInViewModel: LogInViewModel by viewModels()

        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            binding = FragmentResetPasswordBinding.inflate(layoutInflater, container, false)

            setUpActionBar(binding.toolbar, requireActivity())

            clickListeners()
            observe()
            initialize()


            return binding.root
        }

    private fun observe() {
        logInViewModel.newPassword.observe(requireActivity()){

            when (it) {
                is Response.Loading -> {
                    toast(requireContext(), "loading")
                    dialog.show()
                }

                is Response.Success -> {
                    toast(requireContext(), "done")
                    dialog.dismiss()

                }

                is Response.Error -> {
                    toast(requireContext(), it.message!!)
                    dialog.dismiss()
                }
            }
        }

    }
    private fun initialize() {

        dialog = getDialog(requireContext())
        resetLayout = dialog.findViewById<LinearLayout>(R.id.Resetpassword)
        verifyLayout = dialog.findViewById(R.id.verify)
        congratulationLayout = dialog.findViewById<LinearLayout>(R.id.congratulations)

        setPopUpLayout()
    }

    private fun clickListeners() {
        binding.apply {
            ContinueNewpassw.setOnClickListener {

                val email = emailEditText.text.toString()

                if (email.isEmpty()) {
                    emailTextInputLayout.error = "Please enter your email"
                }
                else
                {
                   logInViewModel.resetPassword(email)
                }

            }
        }
    }

    private fun setPopUpLayout(){

        resetLayout.visibility = View.VISIBLE
        verifyLayout.visibility = View.GONE
        congratulationLayout.visibility = View.GONE

    }
}
