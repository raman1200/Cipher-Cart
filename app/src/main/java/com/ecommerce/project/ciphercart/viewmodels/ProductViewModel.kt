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
    val getProdCatData = productRepository.prodCatDataList
    val getWishList = productRepository.wishProdList
    val getSplOffer = productRepository.splOfferList
    val getCartData = productRepository.cartDataList
    val uploaded = productRepository.uploaded
    val deleted = productRepository.deleted
    val prodData = productRepository.prodData

    fun getAllCategory(){
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllCategory()
        }

    }
    fun getAllProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllProduct()
        }

    }
    fun getAllSplOffers() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getAllOffers()
        }

    }
    fun getProductsByCategory(id:Int) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductsByCategory(id)
        }

    }
    fun uploadCartData(data:CartData) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.uploadCartData(data)
        }

    }
    fun getCartData() {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getCartData()
        }

    }
    fun getProductById(prodId:String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductById(prodId)
        }

    }

    fun deleteCartData(id:String) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.deleteCartData(id)
        }

    }

    fun updateCartData(list: List<CartData>) {
        viewModelScope.launch(Dispatchers.IO){
            productRepository.updateCartData(list)
        }
    }

    fun getProductByList(list: List<String>) {
        viewModelScope.launch(Dispatchers.IO) {
            productRepository.getProductByIds(list)
        }
    }

}



