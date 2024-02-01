package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.response.DefaultResponse

interface HomeRepository {
    suspend fun saveRecord(title: String, detail: String, grade: String, category: String,
                           imageFilePath: String): DefaultResponse
}