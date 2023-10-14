package com.vinayakgardi.newshub_newsapplication.Repository

sealed class APIResponses<T>(var data : T?=null , val errorMessage : String? =null) {

    class Loading<T>(): APIResponses<T>()
    class Success<T>(private val mData : T): APIResponses<T>(data = mData)
    class Error<T>(private val error : String): APIResponses<T>(errorMessage = error)

}