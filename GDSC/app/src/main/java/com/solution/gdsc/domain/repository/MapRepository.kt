package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.response.CountRepairResponse
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RequestRepairListResponse
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getAllRepairRecord(): Flow<CountRepairResponse>
    suspend fun getRequestRepairList(districtId: Long): Flow<RequestRepairListResponse>
    suspend fun patchRepairInfo(date:String, repairId: Long): Flow<DefaultResponse>
}