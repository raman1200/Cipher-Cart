package com.ecommerce.project.ciphercart.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.AddressAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentAddressViewBinding
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddressViewFragment : Fragment() {

    lateinit var binding: FragmentAddressViewBinding
    lateinit var addressAdapter: AddressAdapter
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentAddressViewBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        observer()
        clickListeners()

        return binding.root
    }

    private fun observer() {
        binding.apply {
            userViewModel.getAllAddressData.observe(requireActivity()){
                when(it){
                    is Response.Loading -> {
                        pbLoader.visibility = View.VISIBLE
                        noDraft.visibility = View.GONE
                        recyclerView.visibility = View.GONE
                    }
                    is Response.Success -> {
                        it.data?.let { list ->
                            if(list.isEmpty()){
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.VISIBLE
                                recyclerView.visibility = View.GONE
                            }
                            else {
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.GONE
                                recyclerView.visibility = View.VISIBLE
                                addressAdapter.submitList(list)
                            }
                        }
                    }
                    is Response.Error -> {
                        pbLoader.visibility = View.GONE
                        noDraft.visibility = View.VISIBLE
                        recyclerView.visibility = View.GONE
                    }

                }
            }
        }

    }

    private fun initialize() {
        userViewModel.getAllAddress()
        addressAdapter = AddressAdapter()

        binding.apply {
            recyclerView.adapter = addressAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun clickListeners() {
        binding.apply {
            btn.setOnClickListener {
                findNavController().navigate(R.id.action_addressViewFragment_to_addressAddFragment)
            }
        }
    }


}