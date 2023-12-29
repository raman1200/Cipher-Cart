package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserData(
    var name:String="",
    var email:String="",
    var number: String ="",
    var dob:String="",
    var image:String="",
    var uid:String="",
    var token:String="",
    var registerDate:Long=0,
    var emailVerified:Boolean=false
    ) : Parcelable