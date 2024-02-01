package com.solution.gdsc.ui.home.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _saveResult = MutableLiveData<DefaultResponse>()
    val saveResult: LiveData<DefaultResponse> = _saveResult

    fun saveRecord(title: String, detail: String, grade: String, category: String, image: String) {
        viewModelScope.launch {
            try {
                _saveResult.value = homeRepository.saveRecord(
                    title, detail, grade, category, image
                )
            } catch (e: Exception) {
                Log.e("Save Record Error: ", e.message.toString())
            }
        }
    }
}