package com.ecommerce.project.ciphercart.repositories

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import io.grpc.internal.SharedResourceHolder.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask


class RegisterRepository(val context: Context, val firebaseDb: FirebaseDb) {

    val register = MutableLiveData<Response<UserData>>()
    val emailVerified = MutableLiveData<Boolean>()
    fun registerUserByEmail(userData: UserData, password:String){
        register.postValue(Response.Loading())
        emailVerified.postValue(false)
        firebaseDb.createNewUser(userData.email, password).addOnCompleteListener {
            if(it.isSuccessful){   // account created
                val user = it.result.user!!
                userData.uid = user.uid
                sendVerificationEmail(user, userData)
            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }
    private fun checkUserVerified(user:FirebaseUser) : Boolean{
        user.reload()
        return user.isEmailVerified
    }

    fun checkUserByMobile(mobile:String, onResult: (String?, Boolean?) -> Unit){
        firebaseDb.checkUserByMobile(mobile).addOnCompleteListener {
            if(it.isSuccessful) {
                val user = it.result.toObjects(UserData::class.java)
                if (user.isEmpty()) {
                    onResult(null, false)    // number is not register
                } else {
                    onResult(null, true)    // number is already register
                }
            }
            else {
                onResult(it.exception.toString(), null)   // error occur
            }
        }
    }

    private fun saveData(userData: UserData){
        firebaseDb.saveUserInformation(userData).addOnCompleteListener {
            if(it.isSuccessful){
                register.postValue(Response.Success(userData))
            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }
    private fun sendVerificationEmail(user:FirebaseUser, userData: UserData) {
        user.sendEmailVerification().addOnCompleteListener {
            if(it.isSuccessful){
                checkContinuouslyEmailIsVerified(user, userData)
            }
            else{
                register.postValue(Response.Error(it.exception!!.localizedMessage))
            }
        }
    }
    private fun checkContinuouslyEmailIsVerified(user:FirebaseUser, userData: UserData) {
        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                if(checkUserVerified(user)){
                    emailVerified.postValue(true)
                    userData.emailVerified = true

                    // save user data in firebase
                    saveData(userData)


                    Log.d("TAG", "Verified")
                    timer.cancel()
                }
                else{
                    Log.d("TAG", "Not Verified")
                }

            }
        }, 0, 5000)
    }

    fun checkUserByEmail(email: String, onResult: (String?, Boolean?) -> Unit) {
        firebaseDb.checkUserByEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    val user = it.result.toObjects(UserData::class.java)
                    if (user.isEmpty())
                        onResult(null, false)  // email not register
                    else
                        onResult(null, true)  // email is already register
                } else
                    onResult(it.exception.toString(), null)   // error occur
            }
    }
}