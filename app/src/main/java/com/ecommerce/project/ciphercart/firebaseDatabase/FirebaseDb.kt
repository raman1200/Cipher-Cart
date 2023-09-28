package com.ecommerce.project.ciphercart.firebaseDatabase

import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.Constants.Companion.USERS_COLLECTION
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDb {
    private val firebaseAuth = Firebase.auth
    private val usersCollectionRef = Firebase.firestore.collection(USERS_COLLECTION)


    fun createNewUser(email: String, password: String) = firebaseAuth.createUserWithEmailAndPassword(email, password)

    fun saveUserInformation(user: UserData) = usersCollectionRef.document(user.uid).set(user)


    fun checkUserByMobile(mobile:String) = usersCollectionRef.whereEqualTo("number", mobile).get()

    fun checkUserByEmail(email: String) = usersCollectionRef.whereEqualTo("email", email).get()

    fun getUserData(uid : String) = usersCollectionRef.document(uid).get()

    fun resetPassword(email: String) = firebaseAuth.sendPasswordResetEmail(email)


    fun loginUser(
        email: String,
        password: String
    ) = firebaseAuth.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(credential: AuthCredential) = FirebaseAuth.getInstance().signInWithCredential(credential)

    fun logout() = Firebase.auth.signOut()
}