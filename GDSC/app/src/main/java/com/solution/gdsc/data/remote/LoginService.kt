package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/api/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): SignUpResponse

    @POST("/api/auth/login")
    suspend fun login(@Body loginReq: LoginReq): LoginResponse
}