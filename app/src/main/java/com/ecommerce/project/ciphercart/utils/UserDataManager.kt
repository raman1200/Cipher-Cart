package com.ecommerce.project.ciphercart.utils

import android.content.SharedPreferences
import com.ecommerce.project.ciphercart.model.UserData

import javax.inject.Inject

class UserDataManager @Inject constructor(val sharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_USERNAME = "username"
        private const val KEY_EMAIL = "email"
        private const val KEY_MOBILE = "mobile"
        private const val KEY_DOB = "dob"
    }
    fun clearData() {
        val editor = sharedPreferences.edit()
        editor.clear().apply()
    }
    fun saveUserData(userData: UserData?) {
        val editor = sharedPreferences.edit()
        editor.putString(KEY_USERNAME, userData?.name)
        editor.putString(KEY_EMAIL, userData?.email)
        editor.putString(KEY_MOBILE, userData?.number)
        editor.putString(KEY_DOB, userData?.dob)
        editor.apply()
    }

    fun getUsername() = sharedPreferences.getString(KEY_USERNAME, null)

    fun getEmail() = sharedPreferences.getString(KEY_EMAIL, null)

    fun getMobile() = sharedPreferences.getString(KEY_MOBILE, null)

    fun getDob() = sharedPreferences.getString(KEY_DOB, null)



}

