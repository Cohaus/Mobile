package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RepairIdResponse

interface HomeRepository {
    suspend fun saveRecord(title: String, detail: String, grade: String, category: String,
                           imageFilePath: String): DefaultResponse
    suspend fun postRepairBasicRecord(
        title: String, detail: String, category: String,
        placeId: String, address: String, district: String,
        date: String, image: String
    ): RepairIdResponse
}