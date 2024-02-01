package com.solution.gdsc.data.source.home

import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.repository.HomeRepository
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val dateSource: HomeDataSource
) : HomeRepository {
    override suspend fun saveRecord(title: String, detail: String, grade: String, category: String,
                                    imageFilePath: String): DefaultResponse = dateSource.saveRecord(title, detail, grade, category, imageFilePath)
}