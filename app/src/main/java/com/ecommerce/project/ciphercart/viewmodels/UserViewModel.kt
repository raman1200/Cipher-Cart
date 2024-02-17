package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.model.UserData
import com.ecommerce.project.ciphercart.repositories.UserRepository
import com.ecommerce.project.ciphercart.resource.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    val getAllAddressData = userRepository.addresses
    val uploadedAddress = userRepository.uploadAddress

    fun addUserAddress(address: AddressData) {
        userRepository.addUserAddress(address)
    }

    fun getAllAddress() {
        userRepository.getAllAddress()
    }


}