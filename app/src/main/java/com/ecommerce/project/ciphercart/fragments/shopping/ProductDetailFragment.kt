package com.ecommerce.project.ciphercart.fragments.shopping

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide.init
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.databinding.FragmentProductDetailBinding
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.Constants.Companion.RUPEES_SYMBOL
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.setUpActionBar
import com.ecommerce.project.ciphercart.utils.toast
import com.ecommerce.project.ciphercart.viewmodels.ProductViewModel
import com.mcdev.quantitizerlibrary.AnimationStyle
import com.mcdev.quantitizerlibrary.QuantitizerListener
import dagger.hilt.android.AndroidEntryPoint
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem
import javax.inject.Inject

@AndroidEntryPoint
class ProductDetailFragment : Fragment() {

    lateinit var binding:FragmentProductDetailBinding
    private val productViewModel: ProductViewModel by viewModels()
    var prodData : ProductData? = null
    var cart:CartData? = null
    lateinit var cartData: CartData
    private var select = false

    @Inject
    lateinit var userDataManager: UserDataManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailBinding.inflate(layoutInflater, container, false)

        setUpActionBar(binding.toolbar, requireActivity())


        init()
        clickListeners()
        observer()
        liveData()
        getData()
        setDesc()
        setBtn()
        setDil()


        return binding.root
    }

    private fun setDil() {
        val prod = prodData?.prodId ?: cart?.prodId
        val value = userDataManager.isAddedOnProd(prod!!)
        if(value){
            binding.apply {
                favourite.setImageResource(R.drawable.saved2)
                select = true
            }
        }
    }

    private fun setBtn(text:String="Already Added") {
        val prodId = if(prodData!=null){
            prodData!!.prodId
        }else{
            cart!!.prodId
        }

        val value = userDataManager.isAddedOnCart(prodId)
        if(value){
            binding.apply {
                cartBtn.isEnabled = false
                cartBtn.text = text
                cartBtn.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.grey_300))
                cartBtn.setIconTintResource(R.color.grey_800)
                cartBtn.setTextColor(resources.getColor(R.color.grey_800))
            }
        }

    }

    private fun liveData() {

        productViewModel.uploaded.observe(requireActivity()){
            when(it){
                is Response.Loading -> {
//                    toast(requireContext(), "uploading cart data...")
                }
                is Response.Success -> {


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
                prodData?.let {prodData ->
                    cartData = CartData(prodId = prodData.prodId, quantity = hQ.value, prodName = prodData.prodName, prodImage = prodData.images[0], price = prodData.price)
                    productViewModel.uploadCartData(data = cartData)
                    userDataManager.addCartId(prodData.prodId)
                    setBtn("Added in Cart")
                }

            }

            favourite.setOnClickListener{
                val prod = prodData?.prodId ?: cart?.prodId
                if (!select){
                    select = true
                    favourite.setImageResource(R.drawable.saved2)
                    userDataManager.addProdId(prod!!)
                    toast(requireContext(),"Added to Wish list")


                }else{
                    select = false
                    favourite.setImageResource(R.drawable.saved)
                    userDataManager.deleteProdId(prod!!)
                    toast(requireContext(),"Removed From Wish list")


                }

            }
        }
    }

    private fun init() {
        initQuantityBtn()
    }

    private fun observer() {

        productViewModel.prodData.observe(requireActivity()) {
            when(it) {
                is Response.Loading -> {
                    binding.pbLoader.visibility = View.VISIBLE
                    binding.details.visibility = View.INVISIBLE
                    binding.constraintLayout.visibility = View.INVISIBLE


                }
                is Response.Success -> {
                    binding.pbLoader.visibility = View.GONE
                    binding.details.visibility = View.VISIBLE
                    binding.constraintLayout.visibility = View.VISIBLE
                    it.data?.let {data ->
                        setData(data)
                    }

                }
                is Response.Error -> {
                    binding.pbLoader.visibility = View.GONE
                    binding.details.visibility = View.INVISIBLE
                    binding.constraintLayout.visibility = View.INVISIBLE
                    toast(requireContext(),it.message.toString())
                }

            }

        }



        binding.hQ.setQuantitizerListener(object : QuantitizerListener {
            override fun onDecrease() {

            }

            override fun onIncrease() {

            }

            override fun onValueChanged(value: Int) {
                val price = prodData?.price ?:cart?.price
                binding.totalPrice.text = RUPEES_SYMBOL + (value*(price!!)).toString()
            }

        })
    }

    private fun getData() {
        val safeArgs:ProductDetailFragmentArgs by navArgs()
        prodData = safeArgs.productData
        cart = safeArgs.cartData


        cart?.let {
            productViewModel.getProductById(it.prodId)
            binding.hQ.value = it.quantity
        }

        prodData?.let { setData(it) }


    }



    private fun setData(data: ProductData) {
        binding.apply {
            prodName.text  = data.prodName
            desc.text = data.prodDesc
            totalPrice.text = RUPEES_SYMBOL + (hQ.value*(data.price!!)).toString()
            prodPrice.text = RUPEES_SYMBOL + data.price.toString()
        }

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

    override fun onStop() {
        super.onStop()
        productViewModel.prodData.value = null
    }
}


