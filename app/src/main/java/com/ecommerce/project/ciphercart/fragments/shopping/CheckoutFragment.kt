package com.ecommerce.project.ciphercart.fragments.shopping

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.CheckoutAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentCheckoutBinding
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.model.OrderData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.generateOrderId
import com.ecommerce.project.ciphercart.utils.getCurrentDateInLong
import com.ecommerce.project.ciphercart.utils.getDialog
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.OrderViewModel
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import com.ecommerce.project.ciphercart.viewmodels.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckoutFragment : Fragment() {

    private lateinit var binding: FragmentCheckoutBinding
    private lateinit var adapter: CheckoutAdapter
    private lateinit var addressList:List<AddressData>
    private val userViewModel:UserViewModel by viewModels()
    private val productViewModel:ProductViewModel by viewModels()
    private val orderViewModel:OrderViewModel by viewModels()
    private var index = -1
    private lateinit var dialog:Dialog
    private var dialogState = true


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentCheckoutBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar4, requireActivity())
        initialize()
        getAddresses()
        observer()
        setUpAdapter()
        clickListeners()



        return binding.root
    }

    private fun clickListeners() {
        binding.apply {
            addressLists.cardView.setOnClickListener {
                val action = CheckoutFragmentDirections.actionCheckoutFragmentToShippingFragment(
                    addressList.toTypedArray(), index
                )
                findNavController().navigate(action)
            }
            continueBtn.setOnClickListener {
                // save order data
                val i = if(index==-1){
                    0
                }else{
                    index
                }
                val orderData = OrderData(oid = generateOrderId(), orderDate = getCurrentDateInLong(), addressData = addressList[i], prodList = adapter.currentList, status = "ongoing", totalAmount = totalPrice.text.toString())
                orderViewModel.placeOrder(orderData)
            }
        }
    }

    private fun observer() {
        dialog.setOnDismissListener {
            dialog.dismiss()
            if(dialogState)
              findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToHomeFragment())
        }


        orderViewModel.placeOrderStatus.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
//                    toast(requireContext(), "Loading")
                    binding.pbLoader.visibility = View.VISIBLE
                }
                is Response.Success -> {
//                    toast(requireContext(), "Success")
                    binding.pbLoader.visibility = View.GONE
                    dialog.show()
                }
                is Response.Error -> {

                    binding.pbLoader.visibility = View.GONE
                    toast(requireContext(), it.message.toString())
                }
            }
        }

        userViewModel.getAllAddressData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {}
                is Response.Success -> {
                    it.data?.let {
                        addressList = it
                        setAddress()
                    }
                }
                is Response.Error -> {}
            }
        }
        productViewModel.getCartData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {

                }
                is Response.Success -> {
                    it.data?.let { list ->

                        adapter.submitList(list)
                        var price = 0.0
                        for ( l in list){
                            price += l.price* l.quantity
                        }
                        binding.totalPrice.text = Constants.RUPEES_SYMBOL+ price
                        binding.amount.text = Constants.RUPEES_SYMBOL+ price
                    }
                }
                is Response.Error -> {
                    toast(requireContext(), it.message!!)
                }

            }
        }

    }

    private fun setAddress() {
        val args:CheckoutFragmentArgs by navArgs()
        index = args.addressIndex
        if(index == -1){
            addressList.forEach {
                if(it.defaultAddress){
                    binding.addressLists.apply {
                        title.text = it.nameAddress
                        address.text = it.address
                        deft.visibility = View.VISIBLE
                    }
                }
            }
        }
        else{
            binding.addressLists.apply {
                if(addressList[index].defaultAddress)
                    deft.visibility = View.VISIBLE
                title.text = addressList[index].nameAddress
                address.text = addressList[index].address

            }
        }

    }

    private fun getAddresses() {
        userViewModel.getAllAddress()
    }

    private fun setUpAdapter() {
        binding.apply {
            orderRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            orderRecyclerView.adapter = adapter
        }

    }

    private fun initialize() {
        dialog = getDialog(requireContext(), R.layout.orderpopup)
        productViewModel.getCartData()
        adapter = CheckoutAdapter(requireContext())
        val btn = dialog.findViewById<Button>(R.id.orderBtn)

        btn.setOnClickListener {
            dialogState = false
            findNavController().navigate(CheckoutFragmentDirections.actionCheckoutFragmentToMyOrdersFragment())
            dialog.cancel()
        }

    }

    override fun onStop() {
        orderViewModel.placeOrderStatus.postValue(null)
        super.onStop()
    }

}
