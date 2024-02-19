package com.solution.gdsc.data.source.profile

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.data.remote.CoHausService
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserMyPageDatasource @Inject constructor(
    private val coHausService: CoHausService
) {
    suspend fun logout(): Flow<LogoutResponse> = flow {
        val response = coHausService.logout()
        ChallengeApplication.getInstance().tokenManager.deleteToken()
        emit(response)
    }.catch {
        Log.e(TAG, "Logout Failure ${it.message.toString()}")
    }

    suspend fun getUserInfo(): Flow<UserInfoResponse> = flow {
        val response = coHausService.getUserInfo()
        emit(response)
        }.catch {
            ChallengeApplication.getInstance().tokenManager.deleteToken()
            Log.e(TAG, "Get User Info Failure: ${it.message.toString()}")
        }

    suspend fun updateUserInfo(
        updateUserInfoRequest: UpdateUserInfoRequest
    ): Flow<UpdateUserInfoResponse> = flow<UpdateUserInfoResponse> {
        val response = coHausService.updateUserInfo(updateUserInfoRequest)
        emit(response)
    }.catch {
        Log.e(TAG, "Update User Info Failure ${it.message.toString()}")
    }

    suspend fun withdraw(): Flow<DefaultResponse> = flow {
        val response = coHausService.withdraw()
        ChallengeApplication.getInstance().tokenManager.deleteToken()
        emit(response)
    }.catch {
        Log.e(TAG, "Withdraw Failure ${it.message.toString()}")
    }

    suspend fun getUserRecord(): Flow<UserRecordResponse> = flow {
        val response = coHausService.getUserRecord()
        emit(response)
    }.catch {
        Log.e(TAG, "Get User Record Failure ${it.message}")
    }

    suspend fun putVolunteerUser(
        volunteerRegistrationReq: VolunteerRegistrationReq
    ): Flow<VolunteerRegistrationResponse> = flow<VolunteerRegistrationResponse> {
        val response = coHausService.putVolunteerUser(volunteerRegistrationReq)
        emit(response)
    }.catch {
        Log.e(TAG, "Put Volunteer User Failure ${it.message.toString()}")
    }

    fun getRecordInfo(recordId: Long): Flow<SavedRecordResponse> = flow {
        val response = coHausService.getRecordInfo(recordId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Record Info Failure ${it.message}")
    }

    fun deleteSavedRecord(recordId: Long): Flow<DeleteSavedRecordResponse> = flow {
        val response = coHausService.deleteSavedRecord(recordId)
        emit(response)
    }.catch {
        Log.e(TAG, "Delete Saved Record Failure ${it.message}")
    }

    fun updateSavedRecord(recordId: Long, updateSavedRecordReq: UpdateSavedRecordReq): Flow<UpdateSavedRecordResponse> = flow {
        val response = coHausService.updateSavedRecord(recordId, updateSavedRecordReq)
        emit(response)
    }.catch {
        Log.e(TAG, "Update Saved Record Failure ${it.message}")
    }

    suspend fun getRepairsRecord(repairId: Long): Flow<RepairRecordResponse> = flow {
        val response = coHausService.getRepairsRecord(repairId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Repairs Record Failure ${it.message}")
    }

    suspend fun getRepairInfo(repairId: Long): Flow<RepairInfoResponse> = flow {
        val response = coHausService.getRepairInfo(repairId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Repair Info Failure ${it.message}")
    }

    fun getVolunteerRepairList(): Flow<VolunteerRepairListResponse> = flow {
        val response = coHausService.getVolunteerRepairList()
        emit(response)
    }.catch {
        Log.e(TAG, "Get Volunteer Repair List Failure ${it.message}")
    }
}