package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asFlow
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val getCatData = productRepository.catData
    val getProductData = productRepository.prodData
    val getSplOffer = productRepository.splOffer

    fun getAllCategory(){
        productRepository.getAllCategory()
    }
    fun getAllProduct() {
        productRepository.getAllProduct()
    }
    fun getAllSplOffers() {
        productRepository.getAllOffers()
    }
}