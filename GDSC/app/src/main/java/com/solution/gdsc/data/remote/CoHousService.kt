package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import retrofit2.http.DELETE
import retrofit2.http.GET

interface CoHousService {
    // Profile
    @DELETE("/auth/logout")
    suspend fun logout(): DefaultResponse

    @GET("/my-page")
    suspend fun getUserInfo(): UserInfoResponse
}