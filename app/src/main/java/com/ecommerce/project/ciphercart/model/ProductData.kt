package com.ecommerce.project.ciphercart.model

data class ProductData(
    var prodId:Int,
    var prodName:String,
    var catId:Int,
    var images:List<String>,
    var prodDesc:String,
    var price:Double,
    var discount:Double,
    var rating:Double,
    var isVariant:Boolean,
    var isVisible:Boolean
){
    constructor(): this(0,"",0, listOf("image1", "image2"), "", 0.0, 0.0, 0.0, false, false)
}