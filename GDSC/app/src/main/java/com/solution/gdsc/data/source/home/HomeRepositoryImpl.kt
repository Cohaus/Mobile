package com.solution.gdsc.data.source.home

import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairIdResponse
import com.solution.gdsc.domain.repository.HomeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dateSource: HomeDataSource
) : HomeRepository {
    override suspend fun saveRecord(
        title: String, detail: String, grade: String,
        category: String, imageFilePath: String
    ): Flow<DefaultResponse> = dateSource.saveRecord(title, detail, grade, category, imageFilePath)

    override suspend fun postRepairBasicRecord(
        title: String, detail: String, category: String,
        placeId: String, address: String, district: String,
        date: String, image: String
    ): RepairIdResponse = dateSource.postRepairBasicRecord(
        title, detail, category,
        placeId, address, district, date, image
    )
}