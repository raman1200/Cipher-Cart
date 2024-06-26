package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductData(
    var prodId:String,
    var prodName:String,
    var catId:Int,
    var quantity:Int,
    var images:List<String>,
    var prodDesc:String,
    var price:Double,
    var discount:Double,
    var rating:Double,
    var isVariant:Boolean,
    var inEmpty:Boolean
) : Parcelable {
    constructor(): this("","",0,0, listOf("image1", "image2"), "", 0.0, 0.0, 0.0, false, false)
}