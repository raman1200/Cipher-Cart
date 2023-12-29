package com.ecommerce.project.ciphercart.repositories

import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.resource.Response
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject

class ProductRepository @Inject constructor(private val firebaseDb:FirebaseDb) {

    val catData = MutableLiveData<Response<List<CategoryData>>>()
    val prodData = MutableLiveData<Response<List<ProductData>>>()
    val splOffer = MutableLiveData<Response<List<SplOfferData>>>()
    val cartData = MutableLiveData<Response<List<CartData>>>()

    val uid =""        // get uid from sharedPref

    fun uploadCartData(data: CartData) {
        firebaseDb.uploadCartData(data, uid)
    }

    fun getCartData(){

        cartData.postValue(Response.Loading())
        firebaseDb.getCartData(uid).addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<CartData>()
                cartData.postValue(Response.Success(data))
            }
            else{
                cartData.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
    fun getProductById(list: List<CartData>) {
        prodData.postValue(Response.Loading())
        val prodList = mutableListOf<ProductData>()
        list.forEach {cartData ->
            firebaseDb.getProductsById(cartData.prodId).addOnCompleteListener {
                if(it.isSuccessful){
                    val data = it.result.toObject<ProductData>()
                    prodList.add(data!!)
                }
               else {
                    prodData.postValue(Response.Error(it.exception!!.message))
                }
            }
        }
        prodData.postValue(Response.Success(prodList))


    }

    fun getProductsByCategory(catId:Int) {
        prodData.postValue(Response.Loading())
        firebaseDb.getProductsByCategory(catId).addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<ProductData>()
                prodData.postValue(Response.Success(data))
            }
            else{
                prodData.postValue(Response.Error(it.exception!!.message))
            }
        }
    }

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