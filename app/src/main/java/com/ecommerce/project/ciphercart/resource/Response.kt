package com.ecommerce.project.ciphercart.resource

sealed class Response<T>(
    val data :T?=null,
    val message:String?=null
){
    class Loading<T>: Response<T>()
    class Success<T>(successData: T?): Response<T>(data = successData)
    class Error<T>(errorMessage: String?): Response<T>(message = errorMessage)
}
