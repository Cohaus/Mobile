package com.solution.gdsc.ui.map.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.response.CountRepairDto
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairInfoResponse
import com.solution.gdsc.domain.model.response.RepairRecordResponse
import com.solution.gdsc.domain.model.response.RequestRepairItem
import com.solution.gdsc.domain.model.response.WasteFacilityResponse
import com.solution.gdsc.domain.repository.MapRepository
import com.solution.gdsc.domain.repository.UserMyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository,
    private val profileRepository: UserMyPageRepository
) : ViewModel() {

    private val _allRepairRecord = MutableStateFlow<List<CountRepairDto>>(emptyList())
    val allRepairRecord: StateFlow<List<CountRepairDto>> = _allRepairRecord
    private val _requestRepairList = MutableStateFlow<List<RequestRepairItem>>(emptyList())
    val requestRepairList: StateFlow<List<RequestRepairItem>> = _requestRepairList
    private val _repairRecordDetail = MutableStateFlow(RepairRecordResponse(data = null))
    val repairRecordDetail: StateFlow<RepairRecordResponse> = _repairRecordDetail
    private val _repairInfo = MutableStateFlow(RepairInfoResponse(data = null))
    val repairInfo: StateFlow<RepairInfoResponse> = _repairInfo
    private val _patchSuccess = MutableStateFlow(DefaultResponse())
    val patchSuccess: StateFlow<DefaultResponse> = _patchSuccess
    private val _wasteFacilityInfo = MutableStateFlow(WasteFacilityResponse(data = null))
    val wasteFacilityInfo: StateFlow<WasteFacilityResponse> = _wasteFacilityInfo

    fun getAllRepairRecord() {
        viewModelScope.launch {
            try {
                repository.getAllRepairRecord().collect {
                    _allRepairRecord.value = it.data
                }
            } catch (e: Exception) {
                Log.e("Get All Repair Record Error: ", e.message.toString())
            }
        }
    }

    fun getRequestRepairList(districtId: Long) {
        viewModelScope.launch {
            try {
                repository.getRequestRepairList(districtId).collect {
                    _requestRepairList.value = it.data.requestRepairs.content
                }
            } catch (e: Exception) {
                Log.e("Get Request Repair List Error: ", e.message.toString())
            }
        }
    }

    fun getRepairRecordDetail(repairId: Long) {
        viewModelScope.launch {
            try {
                profileRepository.getRepairsRecord(repairId).collect {
                    _repairRecordDetail.value = it
                }
            } catch (e: Exception) {
                Log.e("Get Repair Record Detail Error: ", e.message.toString())
            }
        }
    }

    fun getRepairInfo(repairId: Long) {
        viewModelScope.launch {
            try {
                profileRepository.getRepairInfo(repairId).collect {
                    _repairInfo.value = it
                }
            } catch (e: Exception) {
                Log.e("Get Repair Info Error: ", e.message.toString())
            }
        }
    }

    fun patchRepairInfo(date:String, repairId: Long) {
        viewModelScope.launch {
            try {
                repository.patchRepairInfo("20$date", repairId).collect {
                    _patchSuccess.value = it
                }
            } catch (e: Exception) {
                Log.e("Patch Repair Info Error: ", e.message.toString())
            }
        }
    }

    fun patchRepairComplete(repairId: Long, date: String) {
        viewModelScope.launch {
            try {
                repository.patchRepairComplete(repairId, "20$date").collect {
                    _patchSuccess.value = it
                }
            } catch (e: Exception) {
                Log.e("Patch Repair Complete Error: ", e.message.toString())
            }
        }
    }

    fun getWasteFacilityInfo(repairId: Long) {
        viewModelScope.launch {
            try {
                repository.getWasteFacilityInfo(repairId).collect {
                    _wasteFacilityInfo.value = it
                }
            } catch (e: Exception) {
                Log.e("Get Waste Facility Info Error: ", e.message.toString())
            }
        }
    }
}