package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.adapters.recyclerview.CartAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentMyCartBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.disableButton
import com.ecommerce.project.ciphercart.utils.enableButton
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.FieldPosition
import java.text.NumberFormat
import java.util.Currency

@AndroidEntryPoint
class MyCartFragment : Fragment(), CartAdapter.CartInterface {

    lateinit var binding:FragmentMyCartBinding
    private lateinit var cartAdapter: CartAdapter
    var price = 0.0
    var pp = 0.0
    private val productViewModel: ProductViewModel by viewModels()
    lateinit var cartList: MutableList<CartData>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyCartBinding.inflate(layoutInflater,container,false)




        initialize()
        observer()
        clickListeners()



        return binding.root


    }

    private fun clickListeners() {
        binding.apply {
            checkoutBtn.setOnClickListener {
                val action = MyCartFragmentDirections.actionMyCartFragmentToCheckoutFragment()
                findNavController().navigate(action)
            }
        }
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
            productViewModel.uploaded.observe(requireActivity()){
                when(it) {
                    is Response.Loading -> {
//                        toast(requireContext(),"loading")
                        pbLoader.visibility = View.VISIBLE
                    }
                    is Response.Success -> {
//                        toast(requireContext(),"Uploaded")
                        pbLoader.visibility = View.GONE
                    }
                    is Response.Error -> {
                        pbLoader.visibility = View.GONE
                        toast(requireContext(),it.message.toString())
                    }

                }
            }

            productViewModel.deleted.observe(requireActivity()) {

                when(it) {
                    is Response.Loading -> {}
                    is Response.Success -> {
                        toast(requireContext(),"Item Deleted Successfully")
                        productViewModel.getCartData()
                        productViewModel.deleted.value = null
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
//                          totalPrice.text = NumberFormat.getCurrencyInstance().format(0)
//

                    }
                    is Response.Success -> {
                        it.data?.let { list ->
                            cartList = list.toMutableList()
                            if(list.isEmpty()){
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.VISIBLE
                                cartRecyclerView.visibility = View.GONE
                                checkoutBtn.isEnabled = false
                            }
                            else{
                                checkoutBtn.isEnabled = true
                                cartRecyclerView.visibility = View.VISIBLE
                                pbLoader.visibility = View.GONE
                                noDraft.visibility = View.GONE
                                cartAdapter.submitList(list)

                                price = 0.0
                                for ( l in list){
                                    price += l.price* l.quantity
                                }
                                pp = price
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

    override fun deleteItem(data: CartData) {
        // delete cart item
//        cartAdapter.currentList.remove(data)
        productViewModel.deleteCartData(data.prodId)    //  delete element from firebase
        cartList.remove(data)
        cartAdapter.submitList(cartList)

    }

    override fun onCartItemClick(data: CartData) {
        // goto product detail fragment
        val action = MyCartFragmentDirections.actionMyCartFragmentToProductDetailFragment(cartData = data)
        findNavController().navigate(action)
    }

    override fun hQValue(value:Int, position: Int) {
        cartList[position].quantity = value

        productViewModel.uploadCartData(cartList[position])

        val list = cartAdapter.currentList
        var tp =0.0
        list.forEach{
            tp += it.quantity*it.price
        }
        binding.totalPrice.text = Constants.RUPEES_SYMBOL+ tp
//        binding.totalPrice.text = NumberFormat.getCurrencyInstance().format(tp)
//        toast(requireContext(), binding.totalPrice.text.toString())
    }

    override fun onStop() {
        productViewModel.getCartData.postValue(null)
        productViewModel.getProductData.postValue(null)
        super.onStop()
    }
}



