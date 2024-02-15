package com.ecommerce.project.ciphercart.utils

import android.content.SharedPreferences
import com.ecommerce.project.ciphercart.model.UserData

import javax.inject.Inject

class UserDataManager @Inject constructor(val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_UID = "uid"
        private const val KEY_EMAIL = "email"
        private const val KEY_MOBILE = "mobile"
        private const val KEY_DOB = "dob"
        private const val KEY_IMG = "profile-img"
        private const val KEY_CART_IDS = "cart-prod_ids"
    }
    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }

    fun saveUserData(userData: UserData?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, userData?.name)
        editor.putString(KEY_UID, userData?.uid)
        editor.putString(KEY_EMAIL, userData?.email)
        editor.putString(KEY_MOBILE, userData?.number)
        editor.putString(KEY_DOB, userData?.dob)
        editor.putString(KEY_IMG, userData?.image)
        editor.apply()
    }

    fun getUsername() = sharedPreferences.getString(KEY_USERNAME, null)

    fun getEmail() = sharedPreferences.getString(KEY_EMAIL, null)

    fun getMobile() = sharedPreferences.getString(KEY_MOBILE, null)

    fun getDob() = sharedPreferences.getString(KEY_DOB, null)

    fun getUid() = sharedPreferences.getString(KEY_UID, null)

    fun getProfileImg() = sharedPreferences.getString(KEY_IMG, null)


//    fun addCartId(id:String) {
//        val currentIds = getCartIds()
//        currentIds?.let {
//            currentIds.add(id)
//            saveCartIds(currentIds)
//        }
//    }
//
//    fun isAddedOnCart(id: String):Boolean {
//        val ids = getCartIds()
//        ids?.let {
//            return ids.contains(id)
//        }
//        return false
//    }
//    fun deleteCartId(id: String) {
//        val currentIds = getCartIds()?.toMutableSet() // Convert to mutable set
//        currentIds?.remove(id) // Remove the specified id
//        if (currentIds != null) {
//            saveCartIds(currentIds)
//        } // Save the modified set back to SharedPreferences
//    }
//
//
//    private fun saveCartIds(ids:Set<String>) {
//        val editor = sharedPreferences.edit()
//        editor.putStringSet(KEY_CART_IDS, ids)
//        editor.commit()
//    }
//    fun getCartIds() = sharedPreferences.getStringSet(KEY_CART_IDS, setOf())

    fun addCartId(id: String) {
        val currentIds = getCartIds().toMutableSet()
        currentIds.add(id)
        saveCartIds(currentIds)
    }

    // Function to check if an ID is added to the cart
    fun isAddedOnCart(id: String): Boolean {
        val ids = getCartIds()
        return ids.contains(id)
    }

    // Function to remove an ID from the cart
    fun deleteCartId(id: String) {
        val currentIds = getCartIds().toMutableSet()
        currentIds.remove(id)
        saveCartIds(currentIds)
    }

    // Function to save the cart IDs to SharedPreferences
    private fun saveCartIds(ids: Set<String>) {
        val editor = sharedPreferences.edit()
        editor.putStringSet(KEY_CART_IDS, ids)
        editor.apply()
    }

    // Function to retrieve the cart IDs from SharedPreferences
    fun getCartIds(): Set<String> {
        return sharedPreferences.getStringSet(KEY_CART_IDS, setOf()) ?: setOf()
    }
}

