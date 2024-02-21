package com.ecommerce.project.ciphercart.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.AddressAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentShippingBinding
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShippingFragment : Fragment(), AddressAdapter.AddressInterface {

    private lateinit var binding:FragmentShippingBinding
    private lateinit var addressAdapter: AddressAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShippingBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        clickListeners()
        getData()


        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            applyBtn.setOnClickListener {
                val action = ShippingFragmentDirections.actionShippingFragmentToCheckoutFragment(radioGroup.checkedRadioButtonId)
                findNavController().navigate(action)
            }
        }
    }

    private fun getData() {
        val args : ShippingFragmentArgs by navArgs()
        val list = args.addressData
        val index = args.addressIndex
        addressAdapter.submitList(list.toMutableList())
        for (i in list.indices) {
            if(i==index || list[i].defaultAddress)
                setRadioGroup(i, true)
            else
                setRadioGroup(i)
        }

    }

    private fun setRadioGroup(i:Int, s:Boolean = false) {
        val btn = RadioButton(requireContext())
        btn.layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300)
        btn.layoutDirection = View.LAYOUT_DIRECTION_RTL
        btn.id = i
        if(s)
            btn.isChecked = true
        binding.radioGroup.addView(btn)
    }

    private fun initialize() {
        addressAdapter = AddressAdapter(this, true)
        binding.apply {
            recyclerView.adapter = addressAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }

    }

    override fun updateAddress(data: AddressData) {
    }

}