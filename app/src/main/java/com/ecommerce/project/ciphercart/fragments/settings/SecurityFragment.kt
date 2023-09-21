package com.ecommerce.project.ciphercart.fragments.settings

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.databinding.FragmentAddressAddBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

import com.ecommerce.project.ciphercart.databinding.FragmentProfileBinding
import com.ecommerce.project.ciphercart.databinding.FragmentSecurityBinding



class SecurityFragment : Fragment() {

    lateinit var binding: FragmentSecurityBinding
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentSecurityBinding.inflate(layoutInflater, container, false)


        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        clickListeners()


        return binding.root
    }
    private fun initialize() {
        sharedPreferences = requireActivity().getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }
    private fun clickListeners() {
        binding.apply {
            ChangePassword.setOnClickListener {
                findNavController().navigate(R.id.action_securityFragment_to_createNewPasswordFragment)
            }
            ChangePin.setOnClickListener {
                editor.putInt("verify_value", 2)
                editor.apply()
                findNavController().navigate(R.id.action_securityFragment_to_pinVerifyFragment2)
            }

        }
    }
 }

