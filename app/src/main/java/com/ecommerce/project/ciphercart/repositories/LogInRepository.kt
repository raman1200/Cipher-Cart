package com.ecommerce.project.ciphercart.repositories

import android.content.Context
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.toast
import com.google.firebase.auth.FirebaseAuth
import org.checkerframework.checker.units.qual.s

class LogInRepository(val firebaseDb: FirebaseDb) {

    val logIn = MutableLiveData<Response<UserData>>()
    val newpassword = MutableLiveData<Response<String>>()

    fun LogInUser(email: String, password: String) {
        logIn.postValue(Response.Loading())
        firebaseDb.loginUser(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                firebaseDb.getUserData(it.result.user!!.uid).addOnCompleteListener{
                    if (it.isSuccessful){
                          val userData = it.result.toObject(UserData::class.java)
                          if (userData!= null){
                              logIn.postValue(Response.Success(userData))
                          }else {
                              logIn.postValue(Response.Error("Something went wrong"))
                          }
                    }
                        else{
                        logIn.postValue(Response.Error(it.exception!!.localizedMessage))
                    }
                }
            } else {
                logIn.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }

    }

    fun forgotPassword(email: String){
        newpassword.postValue(Response.Loading())
        firebaseDb.resetPassword(email).addOnCompleteListener {
            if (it.isSuccessful){
              newpassword.postValue(Response.Success(email))

            }
            else{
                newpassword.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }

}