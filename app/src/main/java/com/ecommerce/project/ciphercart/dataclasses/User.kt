package com.ecommerce.project.ciphercart.dataclasses

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class User(val name:String="", val email:String="", val number:String="", val password:String="") : Parcelable