package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse

interface UserMyPageRepository {
    suspend fun logout(): DefaultResponse
    suspend fun getUserInfo(): UserInfoResponse
    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse
    suspend fun withdraw(): DefaultResponse
}