package com.ecommerce.project.ciphercart.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserData(
    var name:String="",
    var email:String="",
    var number: String ="",
    var password:String="",
    var image:String="",
    var uid:String="",
    var token:String="",
    var registerDate:String=""
    ) : Parcelable