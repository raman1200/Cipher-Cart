package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CartData(
    var prodId: String = "",
    var quantity: Int = 0,
    var prodName: String = "",
    var prodImage: String = "",
    var price: Double = 0.0,
) : Parcelable {
}