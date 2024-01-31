package com.solution.gdsc.data.source

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UserInfoDto
import com.solution.gdsc.domain.model.response.UserInfoResponse
import kotlinx.coroutines.Dispatchers
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

    suspend fun getUserInfo(): UserInfoResponse {
        var response = UserInfoResponse(200, "유저 정보 조회 성공",
            UserInfoDto(1, "장민수", "id",
                "010-3211-1234", "ea@af", null, null)
        )
        withContext(Dispatchers.IO) {
            runCatching {
                coHousService.getUserInfo()
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Get User Info Failure")
            }
        }
        return response
    }
}