package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        clickListeners()


        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            editProfile.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_editProfileFragment)
            }
            address.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_addressViewFragment)
            }
            language.setOnClickListener {
                findNavController().navigate(R.id.action_profileFragment_to_languageFragment)
            }
            notification.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_notificationSettingFragment)
            }
            security.setOnClickListener{
                findNavController().navigate(R.id.action_profileFragment_to_securityFragment)
            }
            
        }
    }


}