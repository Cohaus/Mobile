package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.response.DefaultResponse

interface UserMyPageRepository {
    suspend fun logout(): DefaultResponse
}