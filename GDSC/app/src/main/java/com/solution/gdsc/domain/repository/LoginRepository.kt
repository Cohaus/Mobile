package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import kotlinx.coroutines.flow.Flow

interface LoginRepository {
    suspend fun signUp(signUp: SignUpRequest): SignUpResponse
    suspend fun login(loginReq: LoginReq): Flow<LoginResponse>
}