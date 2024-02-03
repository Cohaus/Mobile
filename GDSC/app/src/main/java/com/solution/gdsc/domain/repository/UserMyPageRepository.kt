package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import kotlinx.coroutines.flow.Flow

interface UserMyPageRepository {
    suspend fun logout(): DefaultResponse
    suspend fun getUserInfo(): Flow<UserInfoResponse>
    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse
    suspend fun withdraw(): DefaultResponse
    suspend fun getUserRecord(): UserRecordResponse
}