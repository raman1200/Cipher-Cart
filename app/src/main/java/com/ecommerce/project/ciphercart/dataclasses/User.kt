package com.ecommerce.project.ciphercart.dataclasses

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(
    var name:String="",
    var email:String="",
    var number:Int=0,
    var password:String="",
    var image:String="",
    var uid:String="",
    var token:String="",
    var registerDate:String=""
    ) : Parcelable