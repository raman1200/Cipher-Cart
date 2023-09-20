package com.ecommerce.project.ciphercart.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentAddressAddBinding
import com.ecommerce.project.ciphercart.databinding.FragmentAddressViewBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class AddressViewFragment : Fragment() {

    lateinit var binding: FragmentAddressViewBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddressViewBinding.inflate(layoutInflater, container, false)

//        setUpActionBar(binding.toolbar, requireActivity())

        return binding.root
    }

}