package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.LoginResponse

interface LoginRepository {
    suspend fun signUp(signUp: SignUpRequest): DefaultResponse
    suspend fun login(loginReq: LoginReq): LoginResponse
}