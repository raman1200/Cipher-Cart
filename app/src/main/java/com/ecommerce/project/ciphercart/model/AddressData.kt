package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AddressData(
    var id:String ="",
    var nameAddress:String="",
    var address:String = "",
    var defaultAddress:Boolean = false,
    var latitude:Double = 0.0,
    var longitude:Double = 0.0,
) : Parcelable {
}