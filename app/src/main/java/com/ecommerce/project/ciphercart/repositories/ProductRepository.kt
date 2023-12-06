package com.ecommerce.project.ciphercart.repositories

import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.resource.Response
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class ProductRepository(private val firebaseDb:FirebaseDb) {

    val catData = MutableLiveData<Response<List<CategoryData>>>()
    val prodData = MutableLiveData<Response<List<ProductData>>>()
    val splOffer = MutableLiveData<Response<List<SplOfferData>>>()

    fun getAllCategory() {
        catData.postValue(Response.Loading())
        firebaseDb.getAllCategory().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<CategoryData>()
                catData.postValue(Response.Success(data))
            }
            else{
                catData.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
    fun getAllProduct() {
        prodData.postValue(Response.Loading())
        firebaseDb.getAllProduct().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<ProductData>()
                prodData.postValue(Response.Success(data))
            }
            else{
                prodData.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
    fun getAllOffers() {
        splOffer.postValue(Response.Loading())
        firebaseDb.getAllSplOffers().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<SplOfferData>()
                splOffer.postValue(Response.Success(data))
            }
            else{
                splOffer.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
}