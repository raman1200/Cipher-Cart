package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.repositories.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepository: ProductRepository): ViewModel() {

    val getCatData = productRepository.catData

    fun getAllCategory(){
        productRepository.getAllCategory()
    }
}