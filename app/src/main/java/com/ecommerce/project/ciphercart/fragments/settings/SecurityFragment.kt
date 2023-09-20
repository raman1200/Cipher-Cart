package com.ecommerce.project.ciphercart.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentProfileBinding
import com.ecommerce.project.ciphercart.databinding.FragmentSecurityBinding

class SecurityFragment : Fragment() {

    private lateinit var binding: FragmentSecurityBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSecurityBinding.inflate(layoutInflater, container, false)

        clickListeners()

        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            ChangePassword.setOnClickListener {
                findNavController().navigate(R.id.action_securityFragment_to_createNewPasswordFragment)
            }
            ChangePin.setOnClickListener {
                findNavController().navigate(R.id.action_securityFragment_to_pinVerifyFragment2)
            }

        }
    }
}
