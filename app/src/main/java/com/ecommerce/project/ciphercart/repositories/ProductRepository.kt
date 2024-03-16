package com.ecommerce.project.ciphercart.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.model.SplOfferData
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.google.android.play.core.integrity.p
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import javax.inject.Inject
import kotlin.Exception

class ProductRepository @Inject constructor(private val firebaseDb:FirebaseDb , private val userDataManager: UserDataManager ) {

    val catDataList = MutableLiveData<Response<List<CategoryData>>>()
    val prodDataList = MutableLiveData<Response<List<ProductData>>>()
    val prodCatDataList = MutableLiveData<Response<List<ProductData>>>()
    val wishProdList = MutableLiveData<Response<List<ProductData>>>()
    val splOfferList = MutableLiveData<Response<List<SplOfferData>>>()
    val cartDataList = MutableLiveData<Response<List<CartData>>>()
    val uploaded = MutableLiveData<Response<String>>()
    val deleted = MutableLiveData<Response<String>>()
    val prodData = MutableLiveData<Response<ProductData>>()


    val uid = userDataManager.getUid()        // get uid from sharedPref



    suspend fun deleteCartData(id:String) {
        uid?.let {uid ->
            deleted.postValue(Response.Loading())
            val document = firebaseDb.deleteCartData(id, uid)
                try{
                    userDataManager.deleteCartId(id)
                    deleted.postValue(Response.Success("Deleted"))
                }
                catch (e:Exception){
                    deleted.postValue(Response.Error(e.localizedMessage))
                }
        }?: run {
            deleted.postValue(Response.Error("User ID is null"))
        }


    }


    suspend fun uploadCartData(data: CartData) {
        uid?.let {
            uploaded.postValue(Response.Loading())
            try{
                val document = firebaseDb.uploadCartData(data, uid)
                uploaded.postValue(Response.Success(data.prodId))
            }
            catch (e:Exception){
                uploaded.postValue(Response.Error(e.localizedMessage))
            }
        } ?: run {
            uploaded.postValue(Response.Error("User ID is null"))
        }

    }

    suspend fun getCartData(){
        uid?.let {
            cartDataList.postValue(Response.Loading())
            val document = firebaseDb.getCartData(uid)
            try {
                val data = document.toObjects<CartData>()
                cartDataList.postValue(Response.Success(data))
            }catch (e:Exception){
                e.printStackTrace()
                cartDataList.postValue(Response.Error(e.localizedMessage))
            }
        }?: run {
            cartDataList.postValue(Response.Error("User ID is null"))
        }

    }
    suspend fun getProductById(prodId:String) {
        prodData.postValue(Response.Loading())
        val document = firebaseDb.getProductById(prodId)
        try {
            val data = document.toObject<ProductData>()
            prodData.postValue(Response.Success(data))
        }catch (e:Exception) {
            prodData.postValue(Response.Error(e.localizedMessage))
        }

    }
    suspend fun getProductByIds(prodList:  List<String>) {
        val list: MutableList<ProductData> = ArrayList()
        wishProdList.postValue(Response.Loading())
        for (it in prodList) {
            try {
                val document = firebaseDb.getProductById(it)
                val data = document.toObject<ProductData>()
                data?.let {
                    list.add(it)
                    Log.e("TAG234", list.size.toString())
                }
            } catch (e: Exception) {
                wishProdList.postValue(Response.Error(e.localizedMessage))
            }
        }
        wishProdList.postValue(Response.Success(list))
    }


    suspend fun getProductsByCategory(catId:Int) {
        prodCatDataList.postValue(Response.Loading())
        val document = firebaseDb.getProductsByCategory(catId)
            try{
                val data = document.toObjects<ProductData>()
                prodCatDataList.postValue(Response.Success(data))
            }
            catch (e:Exception){
                prodCatDataList.postValue(Response.Error(e.message))
            }

    }

    suspend fun getAllCategory() {
        catDataList.postValue(Response.Loading())
        val document = firebaseDb.getAllCategory()
            try{
                val data = document.toObjects<CategoryData>()
                catDataList.postValue(Response.Success(data))
            }
            catch (e:Exception){
                catDataList.postValue(Response.Error(e.message))
            }
    }
    suspend fun getAllProduct() {
        prodDataList.postValue(Response.Loading())
        val document = firebaseDb.getAllProduct()
            try{
                val data = document.toObjects<ProductData>()
                prodDataList.postValue(Response.Success(data))
            }
            catch (e:Exception){
                prodDataList.postValue(Response.Error(e.message))
            }

    }
    suspend fun getAllOffers() {
        splOfferList.postValue(Response.Loading())
        val document = firebaseDb.getAllSplOffers()
            try{
                val data = document.toObjects<SplOfferData>()
                splOfferList.postValue(Response.Success(data))
            }
            catch (e:Exception){
                splOfferList.postValue(Response.Error(e.message))
            }

    }

    suspend fun updateCartData(list:List<CartData>){
        list.forEach{
            uid?.let { uid ->
                firebaseDb.updateCartData(it,uid)
            }?: run {
                // uid is null
            }
        }

    }
}