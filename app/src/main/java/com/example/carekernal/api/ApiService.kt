package com.example.carekernal.api

import com.example.carekernal.LoginData
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST


interface ApiService {

    @Headers("Accept: application/json", "Connection: close")
    @FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): LoginData

    @Headers("Accept: application/json", "Connection: close")
    @FormUrlEncoded
    @POST("auth/forgot")
    suspend fun forgotPassword(
        @Field("email") email: String
    ): Unit


    @Headers("Accept: application/json", "Connection: close")
    @FormUrlEncoded
    @POST("auth/verify-code")
    suspend fun verifyOtp(
       @Field("code") code: String,
        @Field("email") email:String
    ): LoginData

}


