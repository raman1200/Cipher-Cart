package com.ecommerce.project.ciphercart.fragments.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentCreateNewPasswordBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class CreateNewPasswordFragment : Fragment() {

    lateinit var binding:FragmentCreateNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateNewPasswordBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())

        // Inflate the layout for this fragment
        return binding.root
    }


}