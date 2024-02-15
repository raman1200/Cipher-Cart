package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val getCatData = productRepository.catDataList
    val getProductData = productRepository.prodDataList
    val getSplOffer = productRepository.splOfferList
    val getCartData = productRepository.cartDataList
    val uploaded = productRepository.uploaded
    val deleted = productRepository.deleted
    val prodData = productRepository.prodData

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
    fun getProductById(prodId:String) {
        productRepository.getProductById(prodId)
    }

    fun deleteCartData(id:String) {
        productRepository.deleteCartData(id)
    }

    fun isAddedOnCart(id:String):Boolean {
        return productRepository.isAddedOnCart(id)
    }

    fun updateCartData(list: List<CartData>) {
        viewModelScope.launch(Dispatchers.IO){
            productRepository.updateCartData(list)
        }
    }

}