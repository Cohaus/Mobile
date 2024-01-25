package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun login(loginReq: LoginReq): Flow<LoginResponse>
}