package com.ecommerce.project.ciphercart.repositories

import android.util.Log
import javax.inject.Inject

interface UserRepo {
    fun saveUser(email :String, password:String)
}
class FirebaseRepo : UserRepo {
    override fun saveUser(email: String, password: String) {
        Log.d("TAG", "Data Saved in Firebase")
    }

}
class SQLRepo @Inject constructor() :UserRepo {
    override fun saveUser(email: String, password: String) {
        Log.d("TAG", "Data Saved in SQL")
    }
}