package com.ecommerce.project.ciphercart.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentAddressAddBinding
import com.ecommerce.project.ciphercart.databinding.FragmentLanguageBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class LanguageFragment : Fragment() {

    lateinit var binding:FragmentLanguageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLanguageBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())



        return binding.root
    }


}