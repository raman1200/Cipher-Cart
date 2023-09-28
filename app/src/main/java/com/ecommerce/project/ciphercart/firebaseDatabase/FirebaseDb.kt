package com.ecommerce.project.ciphercart.firebaseDatabase

import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.Constants.Companion.USERS_COLLECTION
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDb {
    private val firebaseAuth = Firebase.auth
    private val usersCollectionRef = Firebase.firestore.collection(USERS_COLLECTION)


    fun createNewUser(email: String, password: String) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun saveUserInformation(user: UserData) = usersCollectionRef.document(user.uid).set(user)

    fun checkUserVerify():Boolean?{
        val user = firebaseAuth.currentUser ?: return null
        user.reload()
        return user.isEmailVerified
    }
    fun checkUserByMobile(mobile:String, onResult: (String?, Boolean?) -> Unit){
        usersCollectionRef.whereEqualTo("number", mobile).get().addOnCompleteListener {
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
    fun checkUserByEmail(email: String, onResult: (String?, Boolean?) -> Unit) {
        usersCollectionRef.whereEqualTo("email", email).get()
            .addOnCompleteListener {
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

    fun getUserData(user:FirebaseUser) = usersCollectionRef.document(user.uid).get()

    fun resetPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)


    fun loginUser(
        email: String,
        password: String
    ) = firebaseAuth.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(credential: AuthCredential) = FirebaseAuth.getInstance().signInWithCredential(credential)

    fun logout() = Firebase.auth.signOut()
}