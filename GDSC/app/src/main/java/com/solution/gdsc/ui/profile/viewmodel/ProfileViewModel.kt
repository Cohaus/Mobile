package com.solution.gdsc.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UserInfoDto
import com.solution.gdsc.domain.repository.UserMyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userMyPageRepository: UserMyPageRepository
) : ViewModel() {

    private val _isLogout = MutableLiveData<DefaultResponse>()
    val isLogout: LiveData<DefaultResponse> = _isLogout
    private val _userInfo = MutableLiveData<UserInfoDto>()
    val userInfo: LiveData<UserInfoDto> = _userInfo
    private val _isWithdraw = MutableLiveData<DefaultResponse>()
    val isWithdraw: LiveData<DefaultResponse> = _isWithdraw

    fun logout() {
        viewModelScope.launch {
            try {
                _isLogout.value = userMyPageRepository.logout()
            } catch (e: Exception) {
                Log.e("Logout Error: ", e.message.toString())
            }
        }
    }

    fun getUserInfo() {
        viewModelScope.launch {
            try {
                _userInfo.value = userMyPageRepository.getUserInfo().data
            } catch (e: Exception) {
                Log.e("Get User Info Error: ", e.message.toString())
            }
        }
    }

    fun updateUserInfo(
        id: String, name: String, tel: String, email: String,
        userAuthority: String, volunteerType: String?, organizationName: String?
        ) {
        viewModelScope.launch {
            try {
                userMyPageRepository.updateUserInfo(
                    UpdateUserInfoRequest(id, name, tel, email,
                        userAuthority, volunteerType, organizationName)
                )
            } catch (e: Exception) {
                Log.e("Update User Info Error: ", e.message.toString())
            }
        }
    }

    fun withdraw() {
        viewModelScope.launch {
            try {
                _isWithdraw.value = userMyPageRepository.withdraw()
            } catch (e: Exception) {
                Log.e("Withdraw Error: ", e.message.toString())
            }
        }
    }
}