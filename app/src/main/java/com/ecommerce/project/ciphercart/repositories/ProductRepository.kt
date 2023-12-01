package com.ecommerce.project.ciphercart.repositories

import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.CategoryData
import com.ecommerce.project.ciphercart.resource.Response
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects

class ProductRepository(private val firebaseDb:FirebaseDb) {

    val catData = MutableLiveData<Response<List<CategoryData>>>()

    fun getAllCategory(){
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
}