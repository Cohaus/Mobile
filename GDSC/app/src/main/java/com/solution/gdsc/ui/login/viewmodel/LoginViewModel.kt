package com.solution.gdsc.ui.login.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginDto
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

    private val _userInfo = MutableLiveData<LoginDto>()
    val userInfo: LiveData<LoginDto> = _userInfo

    fun signUp(
        id: String, password: String,
        name: String, tel: String, email: String
    ) {
        viewModelScope.launch {
            repository.signUp(SignUpRequest(id, password, name, tel, email)).collect {
                _signUpResult.value = it
            }
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

    fun checkToken() {
        viewModelScope.launch {
            profileRepository.getUserInfo()
        }
    }
}