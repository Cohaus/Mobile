package com.solution.gdsc.ui.login.viewmodel

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import com.solution.gdsc.domain.repository.LoginRepository
import com.solution.gdsc.domain.repository.UserMyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository,
    private val profileRepository: UserMyPageRepository
) : ViewModel() {

    private val _signUpResult = MutableStateFlow(SignUpResponse(data = null))
    val signUpResult get(): StateFlow<SignUpResponse> = _signUpResult

    private val _userInfo = MutableStateFlow(LoginResponse(data = null))
    val userInfo: StateFlow<LoginResponse> = _userInfo

    fun signUp(
        id: String, password: String,
        name: String, tel: String, email: String
    ) {
        viewModelScope.launch {
            try {
                repository.signUp(SignUpRequest(id, password, name, tel, email)).collect {
                    _signUpResult.value = it
                }
            } catch (e: Exception) {
                Log.e(TAG, "SignUp Error: ${e.message.toString()}")
            }
        }
    }

    fun login(id: String, password: String) {
        viewModelScope.launch {
            repository.login(LoginReq(id, password)).collect {
                try {
                    _userInfo.value = it
                    ChallengeApplication.getInstance().tokenManager.saveAccessToken(_userInfo.value.data!!.accessToken)
                    ChallengeApplication.getInstance().tokenManager.saveRefreshToken(_userInfo.value.data!!.refreshToken)
                } catch (e: Exception) {
                    Log.e("Login Error: ", e.message.toString())
                }
            }
        }
    }

    fun checkToken() {
        viewModelScope.launch {
            profileRepository.getUserInfo()
        }
    }
}