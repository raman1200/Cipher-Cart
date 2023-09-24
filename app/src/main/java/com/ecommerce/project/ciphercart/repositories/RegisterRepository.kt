package com.ecommerce.project.ciphercart.repositories

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.grpc.internal.SharedResourceHolder.Resource
import java.util.Timer


class RegisterRepository(val context: Context, val firebaseDb: FirebaseDb) {

    val register = MutableLiveData<Response<UserData>>()
    val registrationResult = MutableLiveData<Boolean>()
    fun registerUserByEmail(userData: UserData){
        register.postValue(Response.Loading())
        firebaseDb.createNewUser(userData.email, userData.password).addOnCompleteListener {
            if(it.isSuccessful){
                val us = it.result.user!!
                userData.uid = it.result.user!!.uid
                sendVerificationEmail(us)
            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }

    }
    fun checkUserVerified(){
    }

    fun saveData(userData: UserData){
        firebaseDb.saveUserInformation(userData).addOnCompleteListener {
            if(it.isSuccessful){
                register.postValue(Response.Success(userData))
            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }
    fun sendVerificationEmail(user:FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener {
            if(it.isSuccessful){

            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }
}