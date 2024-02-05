package com.solution.gdsc.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoDto
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import com.solution.gdsc.domain.repository.UserMyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userMyPageRepository: UserMyPageRepository
) : ViewModel() {

    private val _isLogout = MutableLiveData<DefaultResponse>()
    val isLogout: LiveData<DefaultResponse> = _isLogout
    private val _userInfo = MutableStateFlow(UserInfoDto())
    val userInfo: StateFlow<UserInfoDto> = _userInfo
    private val _isWithdraw = MutableLiveData<DefaultResponse>()
    val isWithdraw: LiveData<DefaultResponse> = _isWithdraw
    private val _isUpdate = MutableLiveData<UpdateUserInfoResponse>()
    val isUpdate: LiveData<UpdateUserInfoResponse> = _isUpdate
    private val _hasResult = MutableLiveData<VolunteerRegistrationResponse>()
    val hasResult: LiveData<VolunteerRegistrationResponse> = _hasResult
    private val _savedRecords = MutableStateFlow<List<RecordItem>>(emptyList())
    val savedRecords: StateFlow<List<RecordItem>> = _savedRecords
    private val _repairRecords = MutableStateFlow<List<RecordItem>>(emptyList())
    val repairRecords: StateFlow<List<RecordItem>> = _repairRecords

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
                userMyPageRepository.getUserInfo().collect {
                    _userInfo.value = it.data
                }
            } catch (e: Exception) {
                Log.e("Get User Info Error: ", e.message.toString())
            }
        }
    }

    fun updateUserInfo(id: String, name: String, tel: String, email: String) {
        viewModelScope.launch {
            try {
                _isUpdate.value = userMyPageRepository.updateUserInfo(
                    UpdateUserInfoRequest(id, name, tel, email)
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

    fun getUserRecord() {
        viewModelScope.launch {
            try {
                userMyPageRepository.getUserRecord().collect {
                    _savedRecords.value = it.data.savedRecord.content
                    _repairRecords.value = it.data.repairRecord.content
                }
                Log.e("Profile", _savedRecords.value.toString())
            } catch (e: Exception) {
                Log.e("User Record Error: ", e.message.toString())
            }
        }
    }

    fun putVolunteerUser(volunteerType: String, organizationName: String?) {
        viewModelScope.launch {
            try {
                _hasResult.value = userMyPageRepository.putVolunteerUser(VolunteerRegistrationReq(volunteerType, organizationName))
            } catch (e: Exception) {
                Log.e("Put Volunteer Error: ", e.message.toString())
            }
        }
    }
}