package com.ecommerce.project.ciphercart.firebaseDatabase

import android.provider.ContactsContract.CommonDataKinds.Phone
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.Constants.Companion.CATEGORIES_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.USERS_COLLECTION
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseDb {
    private val auth = FirebaseAuth.getInstance()
    private val firestore = FirebaseFirestore.getInstance()
    private val usersCollectionRef = firestore.collection(USERS_COLLECTION)
    private val categoryCollectionRef = firestore.collection(CATEGORIES_COLLECTION)



    fun createNewUser(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)

    fun saveUserInformation(user: UserData) = usersCollectionRef.document(user.uid).set(user)

    fun checkUserByMobile(mobile:String) = usersCollectionRef.whereEqualTo("number", mobile).get()

    fun checkUserByEmail(email: String) = usersCollectionRef.whereEqualTo("email", email).get()

    fun getUserData(uid : String) = usersCollectionRef.document(uid).get()

    fun resetPassword(email: String) = auth.sendPasswordResetEmail(email)

//    fun pinGet(credential: AuthCredential) = PhoneAuthProvider.verifyPhoneNumber()

    fun loginUser(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(idToken:String) = auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))


    fun getAllCategory() = categoryCollectionRef.get()



    fun logout() = auth.signOut()
}