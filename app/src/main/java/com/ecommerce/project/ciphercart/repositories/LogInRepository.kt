package com.ecommerce.project.ciphercart.repositories

import android.content.Context
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import com.ecommerce.project.ciphercart.R
import com.ecommerce.project.ciphercart.firebaseDatabase.FirebaseDb
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.resource.Response
import com.ecommerce.project.ciphercart.utils.getCurrentDateInLong
import com.ecommerce.project.ciphercart.viewmodels.LogInViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LogInRepository @Inject constructor(val firebaseDb: FirebaseDb) {

    val logIn = MutableLiveData<Response<UserData>>()
    val newpassword = MutableLiveData<Response<String>>()
    val googleLogin = MutableLiveData<Response<UserData>>()

    fun logout() {
        firebaseDb.logout()
    }
    fun isUserLoggedIn() = firebaseDb.isUserLoggedIn()

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
                val user = task.result.user!!
                val name = user.displayName.toString()
                val email = user.email.toString()
                val number = user.phoneNumber.toString()
                val image = user.photoUrl.toString()
                val uid = user.uid
                val registerDate = getCurrentDateInLong()
                val userData = UserData(name = name,email = email, number =  number, image= image, uid = uid, emailVerified = true, registerDate = registerDate)
                saveData(userData)
            }
            else{
                googleLogin.postValue(Response.Error(task.exception!!.message.toString()))
            }
        }
    }

    private fun saveData(userData: UserData) {
        firebaseDb.saveUserInformation(userData).addOnCompleteListener {
            if(it.isSuccessful){
                googleLogin.postValue(Response.Success(userData))
            }
            else{
                googleLogin.postValue(Response.Error(it.exception!!.message.toString()))
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