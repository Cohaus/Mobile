package com.solution.gdsc.data.source.map

import com.solution.gdsc.domain.model.response.CountRepairResponse
import com.solution.gdsc.domain.repository.MapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MapRepositoryImpl @Inject constructor(
    private val dataSource: MapDataSource
) : MapRepository {
    override suspend fun getAllRepairRecord(): Flow<CountRepairResponse> = dataSource.getAllRepairRecord()
}