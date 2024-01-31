package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.response.DefaultResponse
import retrofit2.http.DELETE

interface CoHousService {
    @DELETE("/auth/logout")
    suspend fun logout(): DefaultResponse
}