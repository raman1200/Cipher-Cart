package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class AddressData(
    var id:String ="",
    var nameAddress:String="",
    var address:String = "",
    var defaultAddress:Boolean = false
) : Parcelable {
}