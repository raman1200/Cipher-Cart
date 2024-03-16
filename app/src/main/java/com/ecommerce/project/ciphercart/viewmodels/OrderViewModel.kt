package com.ecommerce.project.ciphercart.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecommerce.project.ciphercart.model.OrderData
import com.ecommerce.project.ciphercart.repositories.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val orderRepository: OrderRepository):ViewModel() {

    val placeOrderStatus = orderRepository.placeOrderStatus
    val getOnGoingOrders = orderRepository.getOngoingOrders
    val getCompletedOrders = orderRepository.getCompletedOrders


    fun placeOrder(data:OrderData){
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.placeOrder(data)
        }
    }

    fun getOngoingOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getOngoingOrders()
        }
    }

    fun getCompletedOrders() {
        viewModelScope.launch(Dispatchers.IO) {
            orderRepository.getCompletedOrders()
        }
    }

}