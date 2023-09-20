package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentAllNotificationBinding
import com.ecommerce.project.ciphercart.utils.setUpActionBar

class AllNotificationFragment : Fragment() {

    lateinit var binding:FragmentAllNotificationBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllNotificationBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())

        // Inflate the layout for this fragment
        return binding.root
    }

}