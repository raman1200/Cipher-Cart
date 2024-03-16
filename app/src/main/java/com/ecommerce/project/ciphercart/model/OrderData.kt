package com.ecommerce.project.ciphercart.model


data class OrderData(
    var oid:String="",
    var uid:String="",
    var orderDate:Long=0,
    var addressData:AddressData=AddressData(),
    var status:String="",
    var prodList: MutableList<CartData> = ArrayList(),
    var totalAmount:String = "",
)
