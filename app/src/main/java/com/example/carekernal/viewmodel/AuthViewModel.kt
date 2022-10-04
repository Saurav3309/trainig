package com.example.carekernal.viewmodel

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.carekernal.LoginData
import com.example.carekernal.api.Resource
import com.example.carekernal.repository.AuthRepository
import kotlinx.coroutines.launch

class AuthViewModel(
    private var authRepository: AuthRepository
) : ViewModel() {

    private val _loginResponse: MutableLiveData<Resource<LoginData>> = MutableLiveData()
    val loginResponse: LiveData<Resource<LoginData>>
        get() = _loginResponse

    private val _forgotResponse: MutableLiveData<Resource<Unit>> = MutableLiveData()
    val forgotResponse: LiveData<Resource<Unit>>
        get() = _forgotResponse

    private val _verifyResponse: MutableLiveData<Resource<LoginData>> = MutableLiveData()
    val verifyResponse: LiveData<Resource<LoginData>>
        get() = _verifyResponse

    fun login(email: String, password: String) = viewModelScope.launch {
        _loginResponse.value = authRepository.login(email, password)

    }

    fun forgotPassword(email: String) = viewModelScope.launch {
        _forgotResponse.value = authRepository.forgotPassword(email)
    }

    fun verifyOtp(code: String, email: String) = viewModelScope.launch {
        _verifyResponse.value = authRepository.verifyOtp(code, email)
    }



    fun validation() {
        var right=true
        var email: String? = null
        if (email.isNullOrEmpty()) {
            right=false

        } else if (Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            right=false

        }
        if (right){

        }

    }

}