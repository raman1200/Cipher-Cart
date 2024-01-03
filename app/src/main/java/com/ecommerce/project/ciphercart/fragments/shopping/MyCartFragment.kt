package com.ecommerce.project.ciphercart.fragments.shopping

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.activities.MainActivity
import com.ecommerce.project.ciphercart.databinding.FragmentMyCartBinding
import com.ecommerce.project.ciphercart.databinding.FragmentMyOrdersBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel


class MyCartFragment : Fragment() {

    lateinit var binding:FragmentMyCartBinding
    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCartBinding.inflate(layoutInflater,container,false)



        setUpActionBar(binding.toolbar, requireActivity())
//        cartData()
//        getData()


        return binding.root


    }

//    private fun getData() {
//        val safeArgs:ProductDetailFragmentArgs by navArgs()
//        prodData = safeArgs.ProductData
//        setData(prodData)
//    }
//   private fun setData(data: ProductData){
//       binding.totalPrice.text = data.price.toString()
//
//   }

//    private fun cartData(){
//        productViewModel.addcartData().cartData(requireActivity()) {
//
//            when (it) {
//                is Response.Loading -> {
//                    toast(requireContext(), "loading")
//                }
//
//                is Response.Success -> {
//                    toast(requireContext(), "Success")
//                    val intent = Intent(requireActivity(), MainActivity::class.java)
//                    startActivity(intent)
//                }
//
//                is Response.Error -> {
//                    toast(requireContext(), it.message!!)
//                }
//            }
//
//        }


    }


