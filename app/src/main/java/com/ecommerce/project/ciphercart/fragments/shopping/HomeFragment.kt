package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentHomeBinding
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    private val productViewModel:ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)

        clickListeners()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            searchView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchActivity)
            }
            productRecyclerView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_productsViewFragment3)
            }
            saved.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_savedFragment)
            }
            notification.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)
            }

        }
    }

}