package com.ecommerce.project.ciphercart.model


data class CartData(
    var prodId: String = "",
    var quantity: Int = 0,
    var prodName: String = "",
    var prodImage: String = "",
    var price: Double = 0.0,
) {
}