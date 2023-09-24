package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.repositories.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val registerRepository: RegisterRepository) : ViewModel() {

    val register = registerRepository.register

    fun registerUser(userData: UserData) {
        registerRepository.registerUserByEmail(userData)
    }
    fun checkEmailVerify(userData: UserData) {
        registerRepository.checkUserVerified()
    }
}