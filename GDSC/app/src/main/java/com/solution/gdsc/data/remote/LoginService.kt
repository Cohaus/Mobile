package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/api/auth/login")
    suspend fun login(@Body loginReq: LoginReq): LoginResponse
}