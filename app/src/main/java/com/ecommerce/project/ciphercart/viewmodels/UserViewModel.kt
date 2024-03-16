package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.project.ciphercart.model.AddressData
import com.ecommerce.project.ciphercart.repositories.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(private val userRepository: UserRepository):ViewModel() {

    val getAllAddressData = userRepository.addresses
    val uploadedAddress = userRepository.uploadAddress

    fun addUserAddress(address: AddressData) {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.addUserAddress(address)
        }

    }

    fun getAllAddress() {
        viewModelScope.launch(Dispatchers.IO) {
            userRepository.getAllAddress()
        }
    }


}