package com.ecommerce.project.ciphercart.viewmodels

import android.provider.ContactsContract.CommonDataKinds.Email
import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.repositories.LogInRepository
import com.ecommerce.project.ciphercart.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LogInViewModel @Inject constructor (private val logInRepository: LogInRepository): ViewModel() {

    val logIn = logInRepository.logIn
    val newPassword = logInRepository.newpassword

    fun logInbyUser(email: String, password: String) {

        logInRepository.LogInUser(email, password)
    }

    fun resetPassword(email: String){
        logInRepository.forgotPassword(email)
    }

}