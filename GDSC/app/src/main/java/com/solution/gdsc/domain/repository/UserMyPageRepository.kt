package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.DeleteSavedRecordResponse
import com.solution.gdsc.domain.model.response.SavedRecordResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import kotlinx.coroutines.flow.Flow

interface UserMyPageRepository {
    suspend fun logout(): DefaultResponse
    suspend fun getUserInfo(): Flow<UserInfoResponse>
    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse
    suspend fun withdraw(): DefaultResponse
    suspend fun getUserRecord(): Flow<UserRecordResponse>
    suspend fun putVolunteerUser(volunteerRegistrationReq: VolunteerRegistrationReq): VolunteerRegistrationResponse
    suspend fun getSavedRecordInfo(recordId: Long): Flow<SavedRecordResponse>
    suspend fun deleteSavedRecord(recordId: Long): Flow<DeleteSavedRecordResponse>
}