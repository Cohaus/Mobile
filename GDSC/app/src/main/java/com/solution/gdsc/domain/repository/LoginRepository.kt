package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse

interface LoginRepository {
    suspend fun login(loginReq: LoginReq): LoginResponse
}