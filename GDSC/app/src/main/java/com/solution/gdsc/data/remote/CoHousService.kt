package com.solution.gdsc.data.remote

import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH

interface CoHousService {
    // Profile
    @DELETE("/auth/logout")
    suspend fun logout(): DefaultResponse

    @GET("/my-page")
    suspend fun getUserInfo(): UserInfoResponse

    @PATCH("/my-page/info")
    suspend fun updateUserInfo(@Body updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse

    @DELETE("my-page/withdraw")
    suspend fun withdraw(): DefaultResponse
}