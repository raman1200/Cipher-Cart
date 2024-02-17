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


    fun getAllAddress() {
        addresses.postValue(Response.Loading())
        uid?.let {
            firebaseDb.getAllAddress(uid).addOnCompleteListener {
                if(it.isSuccessful){
                    val data = it.result.toObjects<AddressData>()
                    addresses.postValue(Response.Success(data))
                }
                else{
                    addresses.postValue(Response.Error(it.exception!!.message))
                }
            }
        }

    }

    fun addUserAddress(data: AddressData) {

        uid?.let {
            uploadAddress.postValue(Response.Loading())
            val id = UUID.randomUUID().toString()
            data.id = id
            if(data.defaultAddress){
                firebaseDb.findDefaultAddress(uid).addOnCompleteListener {
                    if(it.isSuccessful){
                        val result = it.result.toObjects<AddressData>()
                        if(result.isNotEmpty()) {
                            result.forEach {
                                firebaseDb.removeDefaultAddress(uid, it.id).addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        addAddress(data, uid)
                                    }
                                }
                            }
                        }
                        else{
                            addAddress(data, uid)
                        }

                    }
                }
            }
            else{
                addAddress(data, uid)
            }

        }

    }
    private fun addAddress(data: AddressData, uid:String){
        firebaseDb.addUserAddress(data, uid).addOnCompleteListener {
            if(it.isSuccessful){
                uploadAddress.postValue(Response.Success("Success"))
            }
            else{
                Log.e("ADDRESS", it.exception?.localizedMessage.toString())
                uploadAddress.postValue(Response.Error(it.exception?.localizedMessage))
            }
        }
    }
    

}