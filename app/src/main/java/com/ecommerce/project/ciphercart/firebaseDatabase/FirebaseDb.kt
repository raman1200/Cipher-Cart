package com.ecommerce.project.ciphercart.firebaseDatabase

import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.model.CartData
import com.ecommerce.project.ciphercart.model.OrderData
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.utils.Constants.Companion.ADDRESS_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.CART_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.CATEGORIES_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.ORDER_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.PRODUCTS_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.SPL_OFFERS_COLLECTION
import com.ecommerce.project.ciphercart.utils.Constants.Companion.USERS_COLLECTION
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseDb @Inject constructor(
    private val auth:FirebaseAuth,
    private val firestore:FirebaseFirestore
){
    private val usersCollectionRef = firestore.collection(USERS_COLLECTION)
    private val categoryCollectionRef = firestore.collection(CATEGORIES_COLLECTION)
    private val productCollectionRef = firestore.collection(PRODUCTS_COLLECTION)
    private val splOfferCollectionRef = firestore.collection(SPL_OFFERS_COLLECTION)
    private val orderCollectionRef = firestore.collection(ORDER_COLLECTION)


    // login / register / user info
    fun createNewUser(email: String, password: String) = auth.createUserWithEmailAndPassword(email, password)

    fun saveUserInformation(user: UserData) = usersCollectionRef.document(user.uid).set(user)

    fun checkUserByMobile(mobile:String) = usersCollectionRef.whereEqualTo("number", mobile).get()

    fun checkUserByEmail(email: String) = usersCollectionRef.whereEqualTo("email", email).get()

    fun getUserData(uid : String) = usersCollectionRef.document(uid).get()

    fun resetPassword(email: String) = auth.sendPasswordResetEmail(email)

    fun loginUser(email: String, password: String) = auth.signInWithEmailAndPassword(email, password)

    fun signInWithGoogle(idToken:String) = auth.signInWithCredential(GoogleAuthProvider.getCredential(idToken, null))

    fun isUserLoggedIn() = auth.currentUser

    // category / product
    suspend fun getAllCategory() = categoryCollectionRef.get().await()
    suspend fun getAllProduct() = productCollectionRef.get().await()
    suspend fun getAllSplOffers() = splOfferCollectionRef.get().await()

    suspend fun getProductsByCategory(id:Int) = productCollectionRef.whereEqualTo("catId", id).get().await()

    suspend fun getProductById(prodId:String) = productCollectionRef.document(prodId).get().await()



    // cart
    suspend fun uploadCartData(data:CartData, uid:String) = usersCollectionRef.document(uid).collection(CART_COLLECTION).document(data.prodId).set(data).await()

    suspend fun getCartData(uid:String) = usersCollectionRef.document(uid).collection(CART_COLLECTION).get().await()

    suspend fun deleteCartData(id:String, uid: String) = usersCollectionRef.document(uid).collection(
        CART_COLLECTION).document(id).delete().await()

    suspend fun isAddedOnCart(id:String, uid: String) = usersCollectionRef.document(uid).collection(
        CART_COLLECTION).whereEqualTo("prodId",id).get().await()

    suspend fun updateCartData(cartData:CartData, uid:String) = usersCollectionRef.document(uid).collection(CART_COLLECTION).document(cartData.prodId).set(cartData).await()

    // address
    suspend fun addUserAddress(data:AddressData, uid:String) = usersCollectionRef.document(uid).collection(ADDRESS_COLLECTION).document(data.id).set(data).await()

    suspend fun getAllAddress(uid: String) = usersCollectionRef.document(uid).collection(ADDRESS_COLLECTION).get().await()

    suspend fun findDefaultAddress(uid:String) = usersCollectionRef.document(uid)
        .collection(ADDRESS_COLLECTION).whereEqualTo("defaultAddress",true).get().await()

    suspend fun removeDefaultAddress(uid: String, id: String) = usersCollectionRef.document(uid).collection(
        ADDRESS_COLLECTION).document(id).update("defaultAddress",false).await()


    // order
    suspend fun placeOrder(data:OrderData) = orderCollectionRef.document(data.oid).set(data).await()

    suspend fun getOngoingOrders(uid: String) = orderCollectionRef.whereEqualTo("uid", uid).whereEqualTo("status", "ongoing").get().await()

    suspend fun getCompletedOrders(uid: String) = orderCollectionRef.whereEqualTo("uid", uid).whereEqualTo("status", "completed").get().await()


    // logout
    fun logout() = auth.signOut()



}