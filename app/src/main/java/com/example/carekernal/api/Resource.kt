package com.example.carekernal.api

import okhttp3.ResponseBody

sealed class Resource<out T> {
    data class Sucess<out T>(val values:T) : Resource<T>()
    data class  Failure(
        val isNetworkError : Boolean,
        val errorCode: Int,
        val errorBody:ResponseBody?
    ): Resource<Nothing>()
}