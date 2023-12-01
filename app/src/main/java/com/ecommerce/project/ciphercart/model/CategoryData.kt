package com.ecommerce.project.ciphercart.model



data class CategoryData(var catId:Int, var catName:String, var catImage:String, var catDesc:String){
    constructor(): this(0,"","","")
}

