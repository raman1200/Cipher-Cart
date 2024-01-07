package com.ecommerce.project.ciphercart.fragments.shopping

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.MainActivity
import com.ecommerce.project.ciphercart.adapters.recyclerview.CartAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentMyCartBinding
import com.ecommerce.project.ciphercart.databinding.FragmentMyOrdersBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyCartFragment : Fragment(), CartAdapter.DeleteInterface {

    lateinit var binding:FragmentMyCartBinding
    lateinit var cartAdapter: CartAdapter
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCartBinding.inflate(layoutInflater,container,false)



        setUpActionBar(binding.toolbar, requireActivity())
        initialize()
        observer()



        return binding.root


    }

    private fun initialize() {

        productViewModel.getCartData()
        cartAdapter = CartAdapter(requireContext(), this)

        binding.apply {
            cartRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            cartRecyclerView.adapter = cartAdapter
        }



    }

    private fun observer() {
        binding.apply {

            productViewModel.deleted.observe(requireActivity()) {

                when(it) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        toast(requireContext(),"Item Deleted Successfully")
                        productViewModel.getCartData()
                    }
                    is Response.Error -> {
                        toast(requireContext(),it.message.toString())
                    }

                }

            }




            productViewModel.getCartData.observe(requireActivity()){
                when(it){
                    is Response.Loading -> {
                        pbLoader.visibility = View.VISIBLE
                        noDraft.visibility = View.GONE
                        cartRecyclerView.visibility = View.GONE
                        totalPrice.text = Constants.RUPEES_SYMBOL+ "0.0"
                    }
                    is Response.Success -> {
                        it.data?.let { list ->
                            if(list.isEmpty()){
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.VISIBLE
                                cartRecyclerView.visibility = View.GONE
                            }
                            else{
                                cartRecyclerView.visibility = View.VISIBLE
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.GONE
                                cartAdapter.submitList(list)

                                var price = 0.0
                                for ( l in list){
                                    price += l.price* l.quantity
                                }
                                totalPrice.text = Constants.RUPEES_SYMBOL+ price
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

    override fun deleteItem(id: String) {
        // delete cart item
        productViewModel.deleteCartData(id)
    }


}


