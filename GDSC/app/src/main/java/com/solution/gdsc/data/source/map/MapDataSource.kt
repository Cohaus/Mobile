package com.solution.gdsc.data.source.map

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.CountRepairResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapDataSource @Inject constructor(
    private val coHousService: CoHousService
) {

    suspend fun getAllRepairRecord(): Flow<CountRepairResponse> = flow {
        val response = coHousService.getAllRepairRecord()
        emit(response)
    }.catch {
        Log.e(TAG, "Get All Repair Record Failure ${it.message.toString()}")
    }
}