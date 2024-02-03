package com.solution.gdsc.data.source.profile

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.request.VolunteerRegistrationReq
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.RecordItem
import com.solution.gdsc.domain.model.response.RepairRecord
import com.solution.gdsc.domain.model.response.SaveRecord
import com.solution.gdsc.domain.model.response.UpdateUserInfoDto
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordListResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.model.response.VolunteerInfo
import com.solution.gdsc.domain.model.response.VolunteerRegistrationResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserMyPageDatasource @Inject constructor(
    private val coHousService: CoHousService
) {
    suspend fun logout(): DefaultResponse {
        var response = DefaultResponse(200, "로그아웃 성공", 13)
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.logout()
            }.onSuccess {
                response = it
                ChallengeApplication.getInstance().tokenManager.deleteToken()
            }.onFailure {
                Log.e(TAG, "Logout Failure")
            }
        }
        return response
    }

    suspend fun getUserInfo(): Flow<UserInfoResponse> = flow {
        try {
            val response = coHousService.getUserInfo()
            emit(response)
            delay(1000)
        } catch (e: Exception) {
            Log.e(TAG, "Get User Info Failure")
        }
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

    suspend fun withdraw(): DefaultResponse {
        var response = DefaultResponse(200, "회원탈퇴 성공", 1)
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.withdraw()
            }.onSuccess {
                response = it
                ChallengeApplication.getInstance().tokenManager.deleteToken()
            }.onFailure {
                Log.e(TAG, "Withdraw Failure")
            }
        }
        return response
    }

    suspend fun getUserRecord(): UserRecordResponse {
        var response = UserRecordResponse(status = 200, message = "성공",
            data = UserRecordListResponse(
                RepairRecord(listOf<RecordItem>(), true, first = true, last = true),
                SaveRecord(listOf<RecordItem>(), true, first = true, last = true)))
            withContext(Dispatchers.IO) {
            runCatching {
                coHousService.getUserRecord()
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Get User Record Failure")
            }
        }
        return response
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
}