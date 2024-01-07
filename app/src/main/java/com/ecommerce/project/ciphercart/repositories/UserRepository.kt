package com.ecommerce.project.ciphercart.repositories

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.UserDataManager
import com.google.firebase.firestore.ktx.toObjects
import java.util.UUID
import javax.inject.Inject

class UserRepository (private val firebaseDb: FirebaseDb, private val userDataManager: UserDataManager) {


    val addresses = MutableLiveData<Response<List<AddressData>>>()
    val uid = userDataManager.getUid()


    fun getAllAddress() {
        addresses.postValue(Response.Loading())
        if(uid==null)
            return
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

    fun addUserAddress(data: AddressData) {
        if (uid==null)
            return

        //// Note : (implementation left) set only one default address
        val id = UUID.randomUUID().toString()
        data.id = id
        firebaseDb.addUserAddress(data, uid).addOnCompleteListener {
            if(it.isSuccessful){
               Log.e("ADDRESS", "added")
            }
            else{
                Log.e("ADDRESS", it.exception?.localizedMessage.toString())
            }
        }
    }
    

}