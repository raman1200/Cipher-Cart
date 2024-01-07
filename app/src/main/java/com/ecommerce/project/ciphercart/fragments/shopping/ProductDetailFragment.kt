package com.ecommerce.project.ciphercart.fragments.shopping

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ecommerce.project.ciphercart.databinding.FragmentProductDetailBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants.Companion.RUPEES_SYMBOL
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import com.mcdev.quantitizerlibrary.AnimationStyle
import com.mcdev.quantitizerlibrary.QuantitizerListener
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    lateinit var binding:FragmentProductDetailBinding
    private val productViewModel: ProductViewModel by viewModels()
    lateinit var prodData: ProductData
    lateinit var cartData: CartData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())


        init()
        clickListeners()
        getData()
        initQuantityBtn()
        observer()
        liveData()
        setDesc()


        return binding.root
    }

    private fun liveData() {
        productViewModel.uploaded.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
//                    toast(requireContext(), "uploading cart data...")
                }
                is Response.Success -> {
//                    if(prodData.prodId == it.data){
//                        toast(requireContext(), "Added")
//
//                    }

                }
                is Response.Error -> {
                    toast(requireContext(), it.message!!)
                }

            }
        }
    }



    private fun setDesc() {
        var showMore = false
        binding.showMoreTv.setOnClickListener{
            if(showMore){
                showMore = false
                binding.desc.maxLines = 3
                binding.showMoreTv.text = "Show More..."

            }
            else{
                showMore = true
                binding.desc.maxLines = Int.MAX_VALUE
                binding.showMoreTv.text = "Show Less..."
            }


        }
    }

    private fun clickListeners() {
        binding.apply {
            cartBtn.setOnClickListener {
                cartData = CartData(prodId = prodData.prodId, quantity = hQ.value, prodName = prodData.prodName, prodImage = prodData.images[0], price = prodData.price)

                productViewModel.uploadCartData(data = cartData)
            }
        }
    }

    private fun init() {
        prodData = ProductData()
    }

    private fun observer() {
        binding.hQ.setQuantitizerListener(object : QuantitizerListener {
            override fun onDecrease() {

            }

            override fun onIncrease() {

            }

            override fun onValueChanged(value: Int) {
                binding.totalPrice.text = RUPEES_SYMBOL + (value*(prodData.price)).toString()
            }

        })
    }

    private fun getData() {
        val safeArgs:ProductDetailFragmentArgs by navArgs()
        prodData = safeArgs.ProductData

        setData(prodData)
    }

    private fun setData(data: ProductData) {

        binding.prodName.text  = data.prodName
        binding.desc.text = data.prodDesc
        binding.prodPrice.text = RUPEES_SYMBOL + data.price.toString()
        binding.totalPrice.text = RUPEES_SYMBOL + data.price.toString()

        val sliderList = mutableListOf<CarouselItem>()
        for(img in data.images)
            sliderList.add(CarouselItem(img))
        binding.slider.setData(sliderList)
    }
    private fun initQuantityBtn() {
        val b = binding
        b.hQ.setPlusIconBackgroundColor("#00FFFFFF")
        b.hQ.setMinusIconBackgroundColor("#00FFFFFF")
        b.hQ.setMinusIconColor("#FF000000")
        b.hQ.setPlusIconColor("#FF000000")
        b.hQ.setValueTextColor("#FF000000")
        b.hQ.minValue = 1
        b.hQ.buttonAnimationEnabled = false
        b.hQ.textAnimationStyle = AnimationStyle.FALL_IN
    }



    }


