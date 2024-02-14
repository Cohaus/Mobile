package com.solution.gdsc.data.source.map

import com.solution.gdsc.domain.model.response.CountRepairResponse
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RequestRepairListResponse
import com.solution.gdsc.domain.model.response.WasteFacilityResponse
import com.solution.gdsc.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val dataSource: MapDataSource
) : MapRepository {
    override suspend fun getAllRepairRecord(): Flow<CountRepairResponse> = dataSource.getAllRepairRecord()
    override suspend fun getRequestRepairList(districtId: Long): Flow<RequestRepairListResponse> = dataSource.getRequestRepairList(districtId)
    override suspend fun patchRepairInfo(date: String, repairId: Long): Flow<DefaultResponse> = dataSource.patchRepairInfo(date, repairId)
    override suspend fun patchRepairComplete(repairId: Long, date: String): Flow<DefaultResponse> = dataSource.patchRepairComplete(repairId, date)
    override suspend fun getWasteFacilityInfo(repairId: Long): Flow<WasteFacilityResponse> = dataSource.getWasteFacilityInfo(repairId)
}