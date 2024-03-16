package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.OrderAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentOngoingBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.OrderViewModel
import com.google.firestore.v1.StructuredQuery.Order
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OngoingFragment : Fragment(), OrderAdapter.OrderInterface {

    private lateinit var binding:FragmentOngoingBinding
    private val orderViewModel:OrderViewModel by viewModels()
    private lateinit var cartDataList:MutableList<CartData>
    private lateinit var orderAdapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOngoingBinding.inflate(layoutInflater, container, false)

        initialize()
        observer()



        return binding.root
    }

    private fun observer() {
        binding.apply {
            orderViewModel.getOnGoingOrders.observe(requireActivity()){
                when(it){
                    is Response.Loading -> {
                        pbLoader.visibility = View.VISIBLE
                        noDraft.visibility = View.GONE
                        onGoingRecycler.visibility = View.GONE


                    }
                    is Response.Success -> {
                        it.data?.let { list ->
                            if(list.isEmpty()){
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.VISIBLE
                                onGoingRecycler.visibility = View.GONE

                            }
                            else{
                                onGoingRecycler.visibility = View.VISIBLE
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.GONE
                                it.data.forEach{
                                    cartDataList.addAll(it.prodList)
                                }
                                orderAdapter.submitList(cartDataList)

                            }
                        }
                    }
                    is Response.Error -> {
                        pbLoader.visibility = View.GONE
                        noDraft.visibility = View.VISIBLE
                        toast(requireContext(), it.message!!)
                    }

                }
            }
        }
    }

    private fun initialize() {
        cartDataList = ArrayList()
        orderViewModel.getOngoingOrders()
        setAdapter()
    }

    private fun setAdapter() {
        orderAdapter = OrderAdapter(requireContext(), this)

        binding.apply {
            val llm = LinearLayoutManager(requireContext())
            llm.stackFromEnd = true
            llm.reverseLayout = true
            onGoingRecycler.adapter = orderAdapter
            onGoingRecycler.layoutManager = llm
        }
    }

    override fun onDestroyView() {
        orderViewModel.getOnGoingOrders.postValue(null)
        super.onDestroyView()
    }

    override fun onItemClick(data: CartData) {
        val action = MyOrdersFragmentDirections.actionMyOrdersFragmentToProductDetailFragment(cartData = data)
        findNavController().navigate(action)

    }

}