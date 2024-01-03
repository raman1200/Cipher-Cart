package com.ecommerce.project.ciphercart.fragments.shopping

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.LaunchActivity
import com.ecommerce.project.ciphercart.databinding.FragmentProfileBinding
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private lateinit var binding:FragmentProfileBinding

    @Inject
    lateinit var userDataManager: UserDataManager

    private val logInViewModel:LogInViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)

        initialize()
        clickListeners()


        return binding.root
    }
    private fun initialize() {
        val name = userDataManager.getUsername()
        val number = userDataManager.getMobile()
        val image = userDataManager.getProfileImg()
        if(!name.isNullOrBlank()){
            binding.name.text = name
        }
        if(!number.isNullOrBlank()){
            binding.number.text = number
        }
        if(!image.isNullOrBlank()){
            Glide.with(requireContext()).load(image).placeholder(R.drawable.person_profile).into(binding.profileImg)
        }
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
            logout.setOnClickListener{
                userDataManager.clearData()
                logInViewModel.logout()
                startActivity(Intent(requireActivity(), LaunchActivity::class.java))
            }

            
        }
    }


}