package com.ecommerce.project.ciphercart.repositories

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LogInRepository(val firebaseDb: FirebaseDb) {

    val logIn = MutableLiveData<Response<UserData>>()
    val newpassword = MutableLiveData<Response<String>>()
    val googleLogin = MutableLiveData<Response<UserData>>()


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
    fun getGoogleSignInClient(context:Context):GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(context.getString(R.string.default_web_client_id)).requestEmail().requestProfile().build()

        return  GoogleSignIn.getClient(context, gso)
    }
    fun signInWithGoogle(idToken:String) {
       firebaseDb.signInWithGoogle(idToken).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // save the user details
            }
            else{

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