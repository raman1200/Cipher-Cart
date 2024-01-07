package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val getCatData = productRepository.catData
    val getProductData = productRepository.prodData
    val getSplOffer = productRepository.splOffer
    val getCartData = productRepository.cartData
    val uploaded = productRepository.uploaded
    val deleted = productRepository.deleted

    fun getAllCategory(){
        productRepository.getAllCategory()
    }
    fun getAllProduct() {
        productRepository.getAllProduct()
    }
    fun getAllSplOffers() {
        productRepository.getAllOffers()
    }
    fun getProductsByCategory(id:Int) {
        productRepository.getProductsByCategory(id)
    }
    fun uploadCartData(data:CartData) {
        productRepository.uploadCartData(data)
    }
    fun getCartData() {
        productRepository.getCartData()
    }
    fun getProductsById(list: List<CartData>) {
        productRepository.getProductById(list)
    }

    fun deleteCartData(id:String) {
        productRepository.deleteCartData(id)
    }

}