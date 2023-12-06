package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.CategoryAdapter
import com.ecommerce.project.ciphercart.adapters.recyclerview.ProductAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentHomeBinding
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants
import com.ecommerce.project.ciphercart.utils.TempData
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.ImageCarousel
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class HomeFragment : Fragment() {

    lateinit var binding:FragmentHomeBinding
    lateinit var catAdapter:CategoryAdapter
    lateinit var prodAdapter:ProductAdapter
    lateinit var sliderList:MutableList<CarouselItem>
    private val productViewModel:ProductViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)




        init()
        clickListeners()
        liveData()


        // Inflate the layout for this fragment
        return binding.root
    }

    private fun init() {
        sliderList = mutableListOf<CarouselItem>()
        productViewModel.getAllCategory()
        productViewModel.getAllProduct()
        productViewModel.getAllSplOffers()
        setCatAdapter()
        setProdAdapter()
        setSlider()

    }

    private fun setSlider() {


    }

    private fun setProdAdapter() {
        prodAdapter = ProductAdapter(requireContext())
        binding.productRecyclerView.adapter = prodAdapter
        binding.productRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setCatAdapter() {
        catAdapter = CategoryAdapter(requireContext())
        binding.catRecyclerView.adapter = catAdapter
        binding.catRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    }

    private fun liveData() {
        productViewModel.getSplOffer.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    toast(requireContext(), "Loading Offers")
                }
                is Response.Success -> {
                    for(i in it.data!!)
                        sliderList.add(CarouselItem(i.img))
                    binding.slider.setData(sliderList)
                }
                is Response.Error -> {
                    toast(requireContext(), it.message.toString())
                }
            }
        }
        productViewModel.getProductData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    toast(requireContext(), "Loading product")
                }
                is Response.Success -> {
                    prodAdapter.submitList(it.data)
                }
                is Response.Error -> {
                    toast(requireContext(), it.message.toString())
                }
            }
        }
        productViewModel.getCatData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    toast(requireContext(), "Loading Category")
                }
                is Response.Success -> {
                    toast(requireContext(), it.data!!.size.toString())
                    catAdapter.submitList(it.data)
                }
                is Response.Error -> {
                    toast(requireContext(), it.message.toString())
                }
            }
        }
    }

    private fun clickListeners() {
        binding.apply {
            searchView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_searchActivity)
            }
            productRecyclerView.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_productsViewFragment3)
            }
            saved.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_savedFragment)
            }
            notification.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)

            }

        }
    }

}