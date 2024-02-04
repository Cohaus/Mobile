package com.solution.gdsc.ui.map.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.response.CountRepairDto
import com.solution.gdsc.domain.repository.MapRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: MapRepository
) : ViewModel() {

    private val _allRepairRecord = MutableStateFlow<List<CountRepairDto>>(emptyList())
    val allRepairRecord: StateFlow<List<CountRepairDto>> = _allRepairRecord



    fun getAllRepairRecord() {
        viewModelScope.launch {
            try {
                repository.getAllRepairRecord().collect {
                    _allRepairRecord.value = it.data
                }
            } catch (e: Exception) {
                Log.e("Geta All Repair Record Error: ", e.message.toString())
            }
        }
    }
}