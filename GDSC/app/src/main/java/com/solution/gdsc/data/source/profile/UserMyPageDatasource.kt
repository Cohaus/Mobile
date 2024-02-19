package com.solution.gdsc.data.source.profile

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.data.remote.CoHousService
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
    private val coHousService: CoHousService
) {
    suspend fun logout(): Flow<LogoutResponse> = flow {
        val response = coHousService.logout()
        ChallengeApplication.getInstance().tokenManager.deleteToken()
        emit(response)
    }.catch {
        Log.e(TAG, "Logout Failure ${it.message.toString()}")
    }

    suspend fun getUserInfo(): Flow<UserInfoResponse> = flow {
        val response = coHousService.getUserInfo()
        emit(response)
        }.catch {
            ChallengeApplication.getInstance().tokenManager.deleteToken()
            Log.e(TAG, "Get User Info Failure: ${it.message.toString()}")
        }

    suspend fun updateUserInfo(
        updateUserInfoRequest: UpdateUserInfoRequest
    ): Flow<UpdateUserInfoResponse> = flow<UpdateUserInfoResponse> {
        val response = coHousService.updateUserInfo(updateUserInfoRequest)
        emit(response)
    }.catch {
        Log.e(TAG, "Update User Info Failure ${it.message.toString()}")
    }

    suspend fun withdraw(): Flow<DefaultResponse> = flow {
        val response = coHousService.withdraw()
        ChallengeApplication.getInstance().tokenManager.deleteToken()
        emit(response)
    }.catch {
        Log.e(TAG, "Withdraw Failure ${it.message.toString()}")
    }

    suspend fun getUserRecord(): Flow<UserRecordResponse> = flow {
        val response = coHousService.getUserRecord()
        emit(response)
    }.catch {
        Log.e(TAG, "Get User Record Failure ${it.message}")
    }

    suspend fun putVolunteerUser(
        volunteerRegistrationReq: VolunteerRegistrationReq
    ): Flow<VolunteerRegistrationResponse> = flow<VolunteerRegistrationResponse> {
        val response = coHousService.putVolunteerUser(volunteerRegistrationReq)
        emit(response)
    }.catch {
        Log.e(TAG, "Put Volunteer User Failure ${it.message.toString()}")
    }

    fun getRecordInfo(recordId: Long): Flow<SavedRecordResponse> = flow {
        val response = coHousService.getRecordInfo(recordId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Record Info Failure ${it.message}")
    }

    fun deleteSavedRecord(recordId: Long): Flow<DeleteSavedRecordResponse> = flow {
        val response = coHousService.deleteSavedRecord(recordId)
        emit(response)
    }.catch {
        Log.e(TAG, "Delete Saved Record Failure ${it.message}")
    }

    fun updateSavedRecord(recordId: Long, updateSavedRecordReq: UpdateSavedRecordReq): Flow<UpdateSavedRecordResponse> = flow {
        val response = coHousService.updateSavedRecord(recordId, updateSavedRecordReq)
        emit(response)
    }.catch {
        Log.e(TAG, "Update Saved Record Failure ${it.message}")
    }

    suspend fun getRepairsRecord(repairId: Long): Flow<RepairRecordResponse> = flow {
        val response = coHousService.getRepairsRecord(repairId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Repairs Record Failure ${it.message}")
    }

    suspend fun getRepairInfo(repairId: Long): Flow<RepairInfoResponse> = flow {
        val response = coHousService.getRepairInfo(repairId)
        emit(response)
    }.catch {
        Log.e(TAG, "Get Repair Info Failure ${it.message}")
    }

    fun getVolunteerRepairList(): Flow<VolunteerRepairListResponse> = flow {
        val response = coHousService.getVolunteerRepairList()
        emit(response)
    }.catch {
        Log.e(TAG, "Get Volunteer Repair List Failure ${it.message}")
    }
}