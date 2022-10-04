package com.example.carekernal

import com.google.gson.annotations.SerializedName

data class LoginData(
    @SerializedName("token")
    var token: String? = null
)
