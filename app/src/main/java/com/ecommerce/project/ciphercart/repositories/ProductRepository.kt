package com.ecommerce.project.ciphercart.repositories

import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject

class ProductRepository @Inject constructor(private val firebaseDb:FirebaseDb , private val userDataManager: UserDataManager ) {

    val catDataList = MutableLiveData<Response<List<CategoryData>>>()
    val prodDataList = MutableLiveData<Response<List<ProductData>>>()
    val splOfferList = MutableLiveData<Response<List<SplOfferData>>>()
    val cartDataList = MutableLiveData<Response<List<CartData>>>()
    val uploaded = MutableLiveData<Response<String>>()
    val deleted = MutableLiveData<Response<String>>()
    val prodData = MutableLiveData<Response<ProductData>>()


    val uid = userDataManager.getUid()        // get uid from sharedPref


    fun isAddedOnCart(id:String) : Boolean {
        var bool = false
        uid?.let {

            firebaseDb.isAddedOnCart(id, uid).addOnCompleteListener {
                if(it.isSuccessful){
                    val data = it.result.toObjects<CartData>()
                    if(!data.isEmpty())
                        bool = true
                }
                else{
                    bool = false
                }
            }

        }
        return bool
    }

    fun deleteCartData(id:String) {
        uid?.let {uid ->
            deleted.postValue(Response.Loading())
            firebaseDb.deleteCartData(id, uid).addOnCompleteListener {
                if(it.isSuccessful){
                    userDataManager.deleteCartId(id)
                    deleted.postValue(Response.Success("Deleted"))
                }
                else{
                    deleted.postValue(Response.Error(it.exception?.localizedMessage))
                }
            }
        }

    }


    fun uploadCartData(data: CartData) {
        uploaded.postValue(Response.Loading())
        firebaseDb.uploadCartData(data, uid!!).addOnCompleteListener {
            if(it.isSuccessful) {
                uploaded.postValue(Response.Success(data.prodId))
            }
            else{
                uploaded.postValue(Response.Error(it.exception?.localizedMessage))
            }
        }
    }

    fun getCartData(){
        uid?.let {
            cartDataList.postValue(Response.Loading())
            firebaseDb.getCartData(uid).addOnCompleteListener {
                if(it.isSuccessful){
                    val data = it.result.toObjects<CartData>()
                    cartDataList.postValue(Response.Success(data))
                }
                else{
                    cartDataList.postValue(Response.Error(it.exception!!.message))
                }
            }
        }
    }
    fun getProductById(prodId:String) {
        prodData.postValue(Response.Loading())
        firebaseDb.getProductById(prodId).addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObject<ProductData>()
                prodData.postValue(Response.Success(data))
            }
            else {
                prodData.postValue(Response.Error(it.exception!!.message))
            }
        }
    }

//
//    fun getProductByIds(list: List<CartData>) {
//        prodDataList.postValue(Response.Loading())
//        val prodList = mutableListOf<ProductData>()
//        list.forEach {cartData ->
//            firebaseDb.getProductById(cartData.prodId).addOnCompleteListener {
//                if(it.isSuccessful){
//                    val data = it.result.toObject<ProductData>()
//                    prodList.add(data!!)
//                }
//               else {
//                    prodDataList.postValue(Response.Error(it.exception!!.message))
//                }
//            }
//        }
//        prodDataList.postValue(Response.Success(prodList))
//
//
//    }

    fun getProductsByCategory(catId:Int) {
        prodDataList.postValue(Response.Loading())
        firebaseDb.getProductsByCategory(catId).addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<ProductData>()
                prodDataList.postValue(Response.Success(data))
            }
            else{
                prodDataList.postValue(Response.Error(it.exception!!.message))
            }
        }
    }

    fun getAllCategory() {
        catDataList.postValue(Response.Loading())
        firebaseDb.getAllCategory().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<CategoryData>()
                catDataList.postValue(Response.Success(data))
            }
            else{
                catDataList.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
    fun getAllProduct() {
        prodDataList.postValue(Response.Loading())
        firebaseDb.getAllProduct().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<ProductData>()
                prodDataList.postValue(Response.Success(data))
            }
            else{
                prodDataList.postValue(Response.Error(it.exception!!.message))
            }
        }
    }
    fun getAllOffers() {
        splOfferList.postValue(Response.Loading())
        firebaseDb.getAllSplOffers().addOnCompleteListener {
            if(it.isSuccessful){
                val data = it.result.toObjects<SplOfferData>()
                splOfferList.postValue(Response.Success(data))
            }
            else{
                splOfferList.postValue(Response.Error(it.exception!!.message))
            }
        }
    }

    fun updateCartData(list:List<CartData>){
        list.forEach{
            uid?.let { uid ->
                firebaseDb.updateCartData(it,uid)
            }

        }

    }
}