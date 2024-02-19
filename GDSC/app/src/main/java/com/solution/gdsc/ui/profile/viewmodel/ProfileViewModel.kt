package com.solution.gdsc.ui.profile.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.request.UpdateSavedRecordReq
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.DeleteSavedRecordResponse
import com.solution.gdsc.domain.model.response.LogoutResponse
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.domain.model.response.RepairInfoResponse
import com.solution.gdsc.domain.model.response.RepairRecordResponse
import com.solution.gdsc.domain.model.response.SavedRecordDto
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoDto
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import com.solution.gdsc.domain.model.response.VolunteerRepairListResponse
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
    private val _isLogout = MutableStateFlow(LogoutResponse())
    val isLogout: StateFlow<LogoutResponse> = _isLogout

    private val _userInfo = MutableStateFlow(UserInfoDto())
    val userInfo: StateFlow<UserInfoDto> = _userInfo

    private val _isWithdraw = MutableStateFlow(DefaultResponse())
    val isWithdraw: StateFlow<DefaultResponse> = _isWithdraw

    private val _isUpdate = MutableLiveData<UpdateUserInfoResponse>()
    val isUpdate: LiveData<UpdateUserInfoResponse> = _isUpdate

    private val _hasResult = MutableLiveData<VolunteerRegistrationResponse>()
    val hasResult: LiveData<VolunteerRegistrationResponse> = _hasResult

    private val _savedRecords = MutableStateFlow<List<RecordItem>>(emptyList())
    val savedRecords: StateFlow<List<RecordItem>> = _savedRecords

    private val _repairRecords = MutableStateFlow<List<RecordItem>>(emptyList())
    val repairRecords: StateFlow<List<RecordItem>> = _repairRecords

    private val _savedRecordInfo = MutableStateFlow(SavedRecordDto())
    val savedRecordInfo: StateFlow<SavedRecordDto> = _savedRecordInfo

    private val _deleteSavedRecord = MutableStateFlow(DeleteSavedRecordResponse(1, "", 1))
    val deleteSavedRecord: StateFlow<DeleteSavedRecordResponse> = _deleteSavedRecord

    private val _updateSavedRecord = MutableStateFlow(0)
    val updateSavedRecord: StateFlow<Int> = _updateSavedRecord

    private val _repairRecord = MutableStateFlow(RepairRecordResponse(data = null))
    val repairRecord: StateFlow<RepairRecordResponse> = _repairRecord

    private val _repairInfo = MutableStateFlow(RepairInfoResponse(data = null))
    val repairInfo: StateFlow<RepairInfoResponse> = _repairInfo

    private val _volunteerRepairList = MutableStateFlow(VolunteerRepairListResponse(data = null))
    val volunteerRepairList: StateFlow<VolunteerRepairListResponse> = _volunteerRepairList

    /*fun logout() {
        viewModelScope.launch {
            try {
                _isLogout.value = userMyPageRepository.logout()
            } catch (e: Exception) {
                Log.e("Logout Error: ", e.message.toString())
            }
        }
    }*/

    fun logout() {
        viewModelScope.launch {
            try {
                userMyPageRepository.logout().collect {
                    _isLogout.value = it
                }
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
                userMyPageRepository.withdraw().collect {
                    _isWithdraw.value = it
                }
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

    fun getSaveRecordInfo(recordId: Long) {
        viewModelScope.launch {
            try {
                userMyPageRepository.getSavedRecordInfo(recordId).collect {
                    _savedRecordInfo.value = it.data
                }
            } catch (e: Exception) {
                Log.e("Get Save Record Info Error: ", e.message.toString())
            }
        }
    }

    fun deleteSavedRecord(recordId: Long) {
        viewModelScope.launch {
            try {
                userMyPageRepository.deleteSavedRecord(recordId).collect {
                    _deleteSavedRecord.value = it
                }
            } catch (e: Exception) {
                Log.e("Delete Saved Record Error: ", e.message.toString())
            }
        }
    }

    fun updateSavedRecord(recordId: Long, title: String, detail: String) {
        viewModelScope.launch {
            try {
                userMyPageRepository.updateSavedRecord(recordId, UpdateSavedRecordReq(title, detail)).collect {
                    _updateSavedRecord.value = it.status
                }
            } catch (e: Exception) {
                Log.e("Update Saved Record Error: ", e.message.toString())
            }
        }
    }

    fun getRepairsRecord(repairId: Long) {
        viewModelScope.launch {
            try {
                userMyPageRepository.getRepairsRecord(repairId).collect {
                    if (it.data != null) {
                        _repairRecord.value = it
                    }
                }
            } catch (e: Exception) {
                Log.e("Get Repairs Record Error: ", e.message.toString())
            }
        }
    }

    fun getRepairInfo(repairId: Long) {
        viewModelScope.launch {
            try {
                userMyPageRepository.getRepairInfo(repairId).collect {
                    if (it.data != null) {
                        _repairInfo.value = it
                    }
                }
            } catch (e: Exception) {
                Log.e("Get Repair Info Error: ", e.message.toString())
            }
        }
    }

    fun getVolunteerRepairList() {
        viewModelScope.launch {
            try {
                userMyPageRepository.getVolunteerRepairList().collect {
                    if (it.data != null) _volunteerRepairList.value = it
                }
            } catch (e: Exception) {
                Log.e("Get Volunteer Repair List Error: ", e.message.toString())

            }
        }
    }
}