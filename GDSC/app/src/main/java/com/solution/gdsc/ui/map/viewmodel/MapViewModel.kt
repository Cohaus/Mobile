package com.solution.gdsc.ui.map.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.response.CountRepairDto
import com.solution.gdsc.domain.model.response.RepairRecordResponse
import com.solution.gdsc.domain.model.response.RequestRepairItem
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
}