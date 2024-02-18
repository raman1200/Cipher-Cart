package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.adapters.recyclerview.CheckoutAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentCheckoutBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    lateinit var binding:FragmentCheckoutBinding
    lateinit var adapter: CheckoutAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar4, requireActivity())
        initialize()
        setUpAdapter()
        getData()


        return binding.root
    }

    private fun setUpAdapter() {
        binding.apply {
            orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerView.adapter = adapter
        }

    }

    private fun getData() {
        val args:CheckoutFragmentArgs by navArgs()
        val list = args.cartList

        setData(list)

    }

    private fun setData(list: Array<CartData>) {
        adapter.submitList(list.toMutableList())
        var tp = 0.0
        list.forEach {
            tp += it.price*it.quantity
        }

        binding.amount.text = Constants.RUPEES_SYMBOL + tp
        binding.totalPrice.text = Constants.RUPEES_SYMBOL + tp

    }

    private fun initialize() {
        adapter = CheckoutAdapter(requireContext())
    }


}
