package com.ecommerce.project.ciphercart.fragments.start

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentForgotPasswordBinding
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.disableButton
import com.ecommerce.project.ciphercart.utils.enableButton
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private lateinit var binding:FragmentForgotPasswordBinding
    @Inject
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    private var option=""
    private lateinit var user: UserData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentForgotPasswordBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        getData()
        clickListeners()


        return binding.root
    }

    private fun initialize() {
//        sharedPreferences = requireActivity().getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        disableButton(requireContext(),binding.continueBtn)

    }

    private fun getData() {
        val value = sharedPreferences.getInt("value",0)
//        val args = ForgotPasswordFragmentArgs.fromBundle(requireArguments())
//        user = args.user
        setLayout(value)
    }

    private fun setLayout(value:Int) {
        binding.apply {


            if(value==1){
                title.text = "Verify"
                NumberVerification.visibility = View.VISIBLE
                EmailVerification.visibility = View.VISIBLE
                desc.text = "Select which contact details should you use to verify your account"
                NumberVerification.text = "+91 ${user.number}"
                EmailVerification.text = user.email
            }
            else if(value==2){
                title.text = "Forgot Password"
                NumberVerification.visibility = View.GONE
                EmailVerification.visibility = View.GONE
                desc.text = "Select which contact details should you use to reset your password"
            }


        }
    }

    private fun clickListeners() {
        binding.apply {
            continueBtn.setOnClickListener {
                editor.putInt("verify_value", 1)
                editor.apply()
//                findNavController().navigate(R.id.action_forgotPasswordFragment_to_pinVerifyFragment)
            }
            SMS.setOnClickListener {
                setSMSOption(SMS, Email)
            }
            Email.setOnClickListener {
                setEmailOption(SMS,Email)
            }
        }
    }
    private fun setSMSOption(SMS: MaterialCardView, Email: MaterialCardView) {
        SMS.strokeWidth = resources.getDimension(R.dimen.selected_stroke_width).toInt()
        Email.strokeWidth = resources.getDimension(R.dimen.stroke_width).toInt()
        SMS.strokeColor = resources.getColor(R.color.black, resources.newTheme())
        Email.strokeColor = resources.getColor(R.color.grey_600, resources.newTheme())
        enableButton(requireContext(),binding.continueBtn)

        option = "SMS"
    }
    private fun setEmailOption(SMS: MaterialCardView, Email: MaterialCardView) {
        SMS.strokeWidth = resources.getDimension(R.dimen.stroke_width).toInt()
        Email.strokeWidth = resources.getDimension(R.dimen.selected_stroke_width).toInt()
        SMS.strokeColor = resources.getColor(R.color.grey_600, resources.newTheme())
        Email.strokeColor = resources.getColor(R.color.black, resources.newTheme())
        enableButton(requireContext(),binding.continueBtn)

        option = "Email"
    }

}