package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.adapters.recyclerview.ProductAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentSavedBinding
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SavedFragment : Fragment(), ProductAdapter.OnClick {

    lateinit var binding:FragmentSavedBinding
    lateinit var productAdapter: ProductAdapter

    @Inject
    lateinit var userDataManager: UserDataManager

    private val productViewModel:ProductViewModel by viewModels ()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSavedBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())

        initailize()
        displayWishList()
        observer()


        return binding.root

    }

    private fun initailize() {
        productAdapter = ProductAdapter(requireContext(), this)
        binding.displayList.adapter = productAdapter
        binding.displayList.layoutManager = GridLayoutManager(requireContext(),2)
    }

    private fun observer() {
        productViewModel.getWishList.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
//                    toast(requireContext(), "uploading cart data...")
                      binding.WishLoader.visibility = View.VISIBLE
                      binding.noDraft.visibility = View.GONE

                }
                is Response.Success -> {
//                    toast(requireContext(),it.data?.size.toString())
                    it.data?.let {data ->
                        binding.WishLoader.visibility = View.GONE

                        val wishList = data.toMutableList()
                        if (wishList.isEmpty()) {
                            binding.noDraft.visibility = View.VISIBLE
//                            toast(requireContext(),"No Items Added In Wish list")
                        }else{
                            binding.noDraft.visibility = View.GONE
                            productAdapter.submitList(data)
                        }
                    }

                }
                is Response.Error -> {
                    binding.WishLoader.visibility = View.GONE
                    toast(requireContext(), it.message!!)
                }

            }
        }
    }

    private fun displayWishList() {
        val list = userDataManager.getProdIds().toMutableList()
        productViewModel.getProductByList(list)
    }

    override fun onItemClick(data: ProductData) {
        val action = SavedFragmentDirections.actionSavedFragmentToProductDetailFragment(productData = data)
        findNavController().navigate(action)
    }

    override fun onDestroy() {
        productViewModel.getWishList.postValue(null)
        super.onDestroy()
    }
}