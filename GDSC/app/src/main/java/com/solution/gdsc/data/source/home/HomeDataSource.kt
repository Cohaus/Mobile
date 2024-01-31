package com.solution.gdsc.data.source.home

import android.content.ContentValues
import android.util.Log
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.request.RecordRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class HomeDataSource @Inject constructor(
    private val coHousService: CoHousService
) {
    suspend fun saveRecord(recordRequest: RecordRequest): DefaultResponse {
        var response = DefaultResponse(status = 200, message = "성공", data = 1)
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.saveRecord(recordRequest)
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(ContentValues.TAG, "Save Record Failure")
            }
        }
        return response
    }
}