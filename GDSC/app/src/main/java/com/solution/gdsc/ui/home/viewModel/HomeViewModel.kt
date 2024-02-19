package com.solution.gdsc.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairIdResponse
import com.solution.gdsc.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _saveResult = MutableStateFlow(DefaultResponse())
    val saveResult: StateFlow<DefaultResponse> = _saveResult
    private val _repairBasicRecord = MutableLiveData<RepairIdResponse>()
    val repairBasicRecord: LiveData<RepairIdResponse> = _repairBasicRecord

    fun saveRecord(title: String, detail: String, grade: String, category: String, image: String) {
        viewModelScope.launch {
            try {
                homeRepository.saveRecord(
                    title, detail, grade, category, image
                ).collectLatest {
                    _saveResult.value = it
                }
            } catch (e: Exception) {
                Log.e("Save Record Error: ", e.message.toString())
            }
        }
    }

    fun postRepairBasicRecord(
    title: String, detail: String, category: String,
    placeId: String, address: String, district: String,
    date: String, image: String
    ) {
        viewModelScope.launch {
            try {
                _repairBasicRecord.value = homeRepository.postRepairBasicRecord(
                    title, detail, category, placeId, address, district, date, image
                )
            } catch (e: Exception) {
                Log.e("Post Repair Error: ", e.message.toString())
            }
        }
    }
}