package com.ecommerce.project.ciphercart.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class CategoryData(var catId:Int, var catName:String, var catImage:String, var catDesc:String) :
    Parcelable {
    constructor(): this(0,"","","")
}

