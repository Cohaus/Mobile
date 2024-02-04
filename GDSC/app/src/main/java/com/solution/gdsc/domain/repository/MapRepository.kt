package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.response.CountRepairResponse
import kotlinx.coroutines.flow.Flow

interface MapRepository {
    suspend fun getAllRepairRecord(): Flow<CountRepairResponse>
}