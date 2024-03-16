package com.ecommerce.project.ciphercart.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.google.firebase.firestore.ktx.toObjects
import java.util.UUID

class UserRepository (private val firebaseDb: FirebaseDb, private val userDataManager: UserDataManager) {


    val addresses = MutableLiveData<Response<List<AddressData>>>()
    val uploadAddress = MutableLiveData<Response<String>>()

    private val uid = userDataManager.getUid()


    suspend fun getAllAddress() {
        addresses.postValue(Response.Loading())
        uid?.let {
            val document = firebaseDb.getAllAddress(uid)
                try{
                    val data = document.toObjects<AddressData>()
                    addresses.postValue(Response.Success(data))
                }
                catch (e:Exception){
                    addresses.postValue(Response.Error(e.localizedMessage))
                }
            }


    }

    suspend fun addUserAddress(data: AddressData) {
        uid?.let {
            uploadAddress.postValue(Response.Loading())
            if (data.id.isEmpty()) {
                val id = UUID.randomUUID().toString()
                data.id = id
            }
            if (data.defaultAddress) {
                val document = firebaseDb.findDefaultAddress(uid)
                try {
                    val result = document.toObjects<AddressData>()
                    if (result.isNotEmpty()) {
                        result.forEach {
                            firebaseDb.removeDefaultAddress(uid, it.id)
                            addAddress(data, uid)
                        }
                    }
                } catch (e: Exception) {
                    addAddress(data, uid)
                }
            }
            else{
                addAddress(data, uid)
            }
        }
    }
    private suspend fun addAddress(data: AddressData, uid:String){
        firebaseDb.addUserAddress(data, uid)
            try{
                uploadAddress.postValue(Response.Success("Success"))
            }
            catch (e:Exception){
                e.printStackTrace()
                Log.e("ADDRESS", e.localizedMessage.toString())
                uploadAddress.postValue(Response.Error(e.localizedMessage))
            }

    }
    

}