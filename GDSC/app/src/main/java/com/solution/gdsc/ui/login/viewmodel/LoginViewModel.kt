package com.solution.gdsc.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.LoginDto
import com.solution.gdsc.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
) : ViewModel() {

    private val _signUpResult = MutableLiveData<DefaultResponse>()
    val signUpResult get(): LiveData<DefaultResponse> = _signUpResult

    private val _userInfo = MutableLiveData<LoginDto>()
    val userInfo: LiveData<LoginDto> = _userInfo

    private val _isValidLogin = MutableLiveData<Boolean>()
    val isValidLogin: LiveData<Boolean> = _isValidLogin


    fun signUp(
        id: String, password: String,
        name: String, tel: String, email: String
    ) {
        try {
            viewModelScope.launch {
                _signUpResult.value =
                    repository.signUp(SignUpRequest(id, password, name, tel, email))
            }
        } catch (e: Exception) {
            Log.e("Sign Up Error: ", e.message.toString())
        }
    }

    fun login(id: String, password: String) {
        viewModelScope.launch {
            try {
                val rep = repository.login((LoginReq(id, password)))
                _userInfo.value = rep.data
                ChallengeApplication.getInstance().tokenManager.saveAccessToken(_userInfo.value!!.accessToken)
                ChallengeApplication.getInstance().tokenManager.saveRefreshToken(_userInfo.value!!.refreshToken)
            } catch (e: Exception) {
                Log.e("Login Error: ", e.message.toString())
            }
        }
    }

    fun autoLogin() {
        viewModelScope.launch {
            _isValidLogin.value = ChallengeApplication.getInstance().tokenManager.accessTokenFlow.first()!!.isNotEmpty()
        }
    }

}