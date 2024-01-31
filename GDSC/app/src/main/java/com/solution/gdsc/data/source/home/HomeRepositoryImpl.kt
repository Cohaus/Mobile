package com.solution.gdsc.data.source.home

import com.solution.gdsc.domain.model.request.RecordRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dateSource: HomeDataSource
) : HomeRepository {
    override suspend fun saveRecord(recordRequest: RecordRequest): DefaultResponse = dateSource.saveRecord(recordRequest)
}