package com.solution.gdsc.data.source.map

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.CoHausService
import com.solution.gdsc.domain.model.response.CountRepairResponse
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RequestRepairListResponse
import com.solution.gdsc.domain.model.response.WasteFacilityResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MapDataSource @Inject constructor(
    private val coHausService: CoHausService
) {

    suspend fun getAllRepairRecord(): Flow<CountRepairResponse> = flow {
        val response = coHausService.getAllRepairRecord()
        emit(response)
    }.catch {
        Log.e(TAG, "Get All Repair Record Failure ${it.message.toString()}")
    }

    fun getRequestRepairList(districtId: Long): Flow<RequestRepairListResponse> = flow {
        val response = coHausService.getRequestRepairList(districtId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Request Repair List Failure ${it.message.toString()}")
    }

    fun patchRepairInfo(date: String, repairId: Long): Flow<DefaultResponse> = flow {
        val response = coHausService.patchRepairInfo(repairId, date)
        emit(response)
    }.catch {
        Log.e(TAG, "Patch Repair Info Failure ${it.message.toString()}")
    }

    fun patchRepairComplete(repairId: Long, date: String): Flow<DefaultResponse> = flow {
        val response = coHausService.patchRepairComplete(repairId, date)
        emit(response)
    }.catch {
        Log.e(TAG, "Patch Repair Complete Failure ${it.message.toString()}")
    }

    fun getWasteFacilityInfo(repairId: Long): Flow<WasteFacilityResponse> = flow {
        val response = coHausService.getWasteFacilityInfo(repairId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Waste Facility Info ${it.message}")
    }
}