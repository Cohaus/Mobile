package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.LoginResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {

    @POST("/auth/sign-up")
    suspend fun signUp(@Body signUpRequest: SignUpRequest): DefaultResponse

    @POST("/auth/login")
    suspend fun login(@Body loginReq: LoginReq): LoginResponse
}