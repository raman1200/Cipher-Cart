package com.ecommerce.project.ciphercart.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.OrderData
import com.ecommerce.project.ciphercart.model.ProductData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.ecommerce.project.ciphercart.utils.toast
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firestore.v1.StructuredQuery.Order
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderRepository @Inject constructor(private val firebaseDb:FirebaseDb , private val userDataManager: UserDataManager) {

    val placeOrderStatus = MutableLiveData<Response<String>>()
    val getOngoingOrders = MutableLiveData<Response<List<OrderData>>>()
    val getCompletedOrders = MutableLiveData<Response<List<OrderData>>>()


    val uid = userDataManager.getUid()        // get uid from sharedPref


    suspend fun placeOrder(data: OrderData) {
        uid?.let { uid ->
            placeOrderStatus.postValue(Response.Loading())
            try {
                data.uid = uid
                withContext(Dispatchers.IO){
                    firebaseDb.placeOrder(data)
                }
                clearCart()
                placeOrderStatus.postValue(Response.Success("Saved"))
            } catch (e: Exception) {
                placeOrderStatus.postValue(Response.Error(e.localizedMessage ?: "Failed to place order"))
            }
        } ?: run {
            placeOrderStatus.postValue(Response.Error("User ID is null"))
        }
    }
    private suspend fun clearCart(){
        uid?.let {
            val document = firebaseDb.getCartData(uid)
            try {
                for (doc in document) {
                    doc.reference.delete()
                    userDataManager.deleteCartId(doc.id)
                }
            }catch (e:Exception){
                e.printStackTrace()
            }

        }?: run {
           Log.e("UID","User ID is null")
        }

    }


    suspend fun getOngoingOrders() {
        uid?.let {
            getOngoingOrders.postValue(Response.Loading())
            val document = firebaseDb.getOngoingOrders(uid)
                try{
                    val data = document.toObjects<OrderData>()
                    getOngoingOrders.postValue(Response.Success(data))
                }
                catch (e:Exception){
                    e.printStackTrace()
                    getOngoingOrders.postValue(Response.Error(e.localizedMessage))
                }

        } ?: run {
            getOngoingOrders.postValue(Response.Error("User ID is null"))
        }
    }
    suspend fun getCompletedOrders() {
        uid?.let {
            getCompletedOrders.postValue(Response.Loading())
            val document = firebaseDb.getCompletedOrders(uid)
                try {
                    val data = document.toObjects<OrderData>()
                    getCompletedOrders.postValue(Response.Success(data))
                }
                catch (e:Exception){
                    e.printStackTrace()
                    getCompletedOrders.postValue(Response.Error(e.localizedMessage))
                }
        } ?: run {
            getCompletedOrders.postValue(Response.Error("User ID is null"))
        }
    }
}