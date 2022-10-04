package com.example.carekernal.repository

import com.example.carekernal.api.ApiService

class AuthRepository(
    private val api: ApiService
) : BaseRepository() {

    suspend fun login(email: String, password: String) = safeApiCall {
        api.userLogin(email, password)
    }

    suspend fun forgotPassword(email: String) = safeApiCall {
        api.forgotPassword(email)

    }

    suspend fun verifyOtp(code: String,email: String) = safeApiCall {
        api.verifyOtp(code,email)
    }


}