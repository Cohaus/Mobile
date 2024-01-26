package com.solution.gdsc.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import com.solution.gdsc.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    private val _signUpResult = MutableLiveData<SignUpResponse>()
    val signUpResult get() = _signUpResult

    private val _userInfo = MutableLiveData<LoginResponse>()
    val userInfo: LiveData<LoginResponse> = _userInfo


    fun signUp(
        id: String, password: String,
        name: String, tel: String, email: String
    ) {
        try {
            viewModelScope.launch {
                _signUpResult.value = repository.signUp(SignUpRequest(id, password, name, tel, email))
            }
        } catch (e: Exception) {
            Log.e("Sign Up Error: ", e.message.toString())
        }
    }

    fun login(id: String, password: String) {
        viewModelScope.launch {
            try {
                repository.login(LoginReq(id, password)).collect {
                    _userInfo.value = it
                }
            } catch (e: Exception) {
                Log.e("Login Error: ", e.message.toString())
            }
        }
    }
}