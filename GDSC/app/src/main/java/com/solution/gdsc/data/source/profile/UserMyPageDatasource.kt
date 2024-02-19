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
import com.solution.gdsc.domain.model.response.UpdateUserInfoDto
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.model.response.VolunteerInfo
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import com.solution.gdsc.domain.model.response.VolunteerRepairListResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
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

    suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse {
        var response = UpdateUserInfoResponse(200, "요청에 성공하였습니다.",
            UpdateUserInfoDto(1, "장민수", "cty123", "mais2@ag.com", "010-1234-5678")
            )
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.updateUserInfo(updateUserInfoRequest)
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Update User Info Failure")
            }
        }
        return response
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

    suspend fun putVolunteerUser(volunteerRegistrationReq: VolunteerRegistrationReq): VolunteerRegistrationResponse {
        var response = VolunteerRegistrationResponse(1, "성공",
            VolunteerInfo("a", null)
        )
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.putVolunteerUser(volunteerRegistrationReq)
            }.onSuccess {
                response =  it
                delay(1000)
            }.onFailure {
                Log.e(TAG, "Put Volunteer User Failure")
            }
        }
        return response
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