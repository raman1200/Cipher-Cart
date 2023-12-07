package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide.init
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.ProductAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentProductsViewBinding
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class ProductsViewFragment : Fragment(),ProductAdapter.OnClick {

    lateinit var binding:FragmentProductsViewBinding
    lateinit var catData:CategoryData
    private val productViewModel: ProductViewModel by viewModels()
    lateinit var sliderList:MutableList<CarouselItem>
    lateinit var prodAdapter: ProductAdapter



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentProductsViewBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())
        init()
        getData()
        liveData()
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun init() {
        sliderList = mutableListOf<CarouselItem>()
        setProdAdapter()
    }
    private fun setProdAdapter() {
        prodAdapter = ProductAdapter(requireContext(), this)
        binding.productRecyclerView.adapter = prodAdapter
        binding.productRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun liveData() {
        productViewModel.getProductData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    binding.pb.visibility = View.VISIBLE

                }
                is Response.Success -> {
                    binding.pb.visibility = View.GONE
                    if(it.data!!.size>0){
                        prodAdapter.submitList(it.data)
                    }
                    else{
                        binding.noTv.visibility = View.VISIBLE
                    }

                }
                is Response.Error -> {
                    binding.pb.visibility = View.GONE
                    toast(requireContext(), it.message.toString())
                }
            }
        }
    }

    private fun getData() {
        val args: ProductsViewFragmentArgs by navArgs()
        catData = args.CategoryData


        binding.categoryName.text = catData.catName

        productViewModel.getProductsByCategory(catData.catId)

    }

    override fun onItemClick(data: ProductData) {
        val action = ProductsViewFragmentDirections.actionProductsViewFragment3ToProductDetailFragment2(data)
        findNavController().navigate(action)
    }


}