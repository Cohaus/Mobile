package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.RecordRequest
import com.solution.gdsc.domain.model.response.DefaultResponse

interface HomeRepository {
    suspend fun saveRecord(recordRequest: RecordRequest): DefaultResponse
}