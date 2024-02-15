package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.adapters.recyclerview.CategoryAdapter
import com.ecommerce.project.ciphercart.adapters.recyclerview.ProductAdapter
import com.ecommerce.project.ciphercart.databinding.FragmentHomeBinding
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(), ProductAdapter.OnClick, CategoryAdapter.CategoryItemClick {

    lateinit var binding:FragmentHomeBinding
    lateinit var catAdapter:CategoryAdapter
    lateinit var prodAdapter:ProductAdapter
    @Inject
    lateinit var userDataManager: UserDataManager
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
        productViewModel.getAllCategory()
        productViewModel.getAllProduct()
        productViewModel.getAllSplOffers()
        setCatAdapter()
        setProdAdapter()
        setSlider()
        val name = userDataManager.getUsername()
        val image = userDataManager.getProfileImg()
        if(!name.isNullOrEmpty())
            binding.name.text = name
       if(!image.isNullOrEmpty())
           Glide.with(requireContext()).load(image).placeholder(R.drawable.person).into(binding.profileImg)
    }

    private fun setSlider() {

    }

    private fun setProdAdapter() {
        prodAdapter = ProductAdapter(requireContext(), this)
        binding.productRecyclerView.adapter = prodAdapter
        binding.productRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2)
    }

    private fun setCatAdapter() {
        catAdapter = CategoryAdapter(requireContext(), this)
        binding.catRecyclerView.adapter = catAdapter
        binding.catRecyclerView.layoutManager = GridLayoutManager(requireContext(), 4)
    }

    private fun liveData() {
        productViewModel.getSplOffer.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
//                    toast(requireContext(), "Loading Offers")
                }
                is Response.Success -> {
                    val sliderList = mutableListOf<CarouselItem>()
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
//                    toast(requireContext(), "Loading product")
                    binding.prodPb.visibility = View.VISIBLE
                }
                is Response.Success -> {
                    binding.prodPb.visibility = View.GONE
                    prodAdapter.submitList(it.data)
                }
                is Response.Error -> {
                    binding.prodPb.visibility = View.GONE
                    toast(requireContext(), it.message.toString())
                }
            }
        }
        productViewModel.getCatData.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
                    binding.catPb.visibility = View.VISIBLE
//                    toast(requireContext(), "Loading Category")
                }
                is Response.Success -> {
                    binding.catPb.visibility = View.GONE
                    catAdapter.submitList(it.data)
                }
                is Response.Error -> {
                    binding.catPb.visibility = View.GONE
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
                findNavController().navigate(R.id.action_homeFragment_to_productDetailFragment)
            }
            saved.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_savedFragment)
            }
            notification.setOnClickListener{
                findNavController().navigate(R.id.action_homeFragment_to_notificationFragment)

            }
        }
    }

    override fun onItemClick(data: ProductData) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductDetailFragment(productData = data)
        findNavController().navigate(action)
    }

    override fun onCategoryItemClick(data: CategoryData) {
        val action = HomeFragmentDirections.actionHomeFragmentToProductsViewFragment(data)
        findNavController().navigate(action)
    }

}