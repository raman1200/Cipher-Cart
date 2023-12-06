package com.ecommerce.project.ciphercart.model

data class SplOfferData(val id:Int,val img:String, val validFrom:String, val validUpto:String) {
    constructor() : this(0,"","","")
}