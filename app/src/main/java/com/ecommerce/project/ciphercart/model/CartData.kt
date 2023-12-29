package com.ecommerce.project.ciphercart.model


data class CartData(
    var prodId:String,
    var quantity:Int,
    var prodName:String,
    var prodImage:String,
    var price:Double
) {
}