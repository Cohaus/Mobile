package com.solution.gdsc.domain.repository

import com.solution.gdsc.domain.model.request.UpdateSavedRecordReq
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.DeleteSavedRecordResponse
import com.solution.gdsc.domain.model.response.LogoutResponse
import com.solution.gdsc.domain.model.response.RepairInfoResponse
import com.solution.gdsc.domain.model.response.RepairRecordResponse
import com.solution.gdsc.domain.model.response.SavedRecordResponse
import com.solution.gdsc.domain.model.response.UpdateSavedRecordResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import com.solution.gdsc.domain.model.response.VolunteerRepairListResponse
import kotlinx.coroutines.flow.Flow

interface UserMyPageRepository {
    suspend fun logout(): Flow<LogoutResponse>
    suspend fun getUserInfo(): Flow<UserInfoResponse>
    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse
    suspend fun withdraw(): DefaultResponse
    suspend fun getUserRecord(): Flow<UserRecordResponse>
    suspend fun putVolunteerUser(volunteerRegistrationReq: VolunteerRegistrationReq): VolunteerRegistrationResponse
    suspend fun getSavedRecordInfo(recordId: Long): Flow<SavedRecordResponse>
    suspend fun deleteSavedRecord(recordId: Long): Flow<DeleteSavedRecordResponse>
    suspend fun updateSavedRecord(recordId: Long, updateSavedRecordReq: UpdateSavedRecordReq): Flow<UpdateSavedRecordResponse>
    suspend fun getRepairsRecord(repairId: Long): Flow<RepairRecordResponse>
    suspend fun getRepairInfo(repairId: Long): Flow<RepairInfoResponse>
    suspend fun getVolunteerRepairList(): Flow<VolunteerRepairListResponse>
}