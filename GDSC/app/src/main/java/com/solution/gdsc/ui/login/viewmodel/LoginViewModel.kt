package com.solution.gdsc.ui.login.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.repository.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: LoginRepository
) : ViewModel() {

    private val _userInfo = MutableLiveData<LoginResponse>()
    val userInfo: LiveData<LoginResponse> = _userInfo

    fun login(id: String, password: String) {
        viewModelScope.launch {
            val res = repository.login(LoginReq(id, password))
            _userInfo.value = res
        }
    }
}