package com.solution.gdsc.data.source

import android.content.ContentValues
import android.util.Log
import com.solution.gdsc.ChallengeApplication
import com.solution.gdsc.data.remote.CoHousService
import com.solution.gdsc.domain.model.response.DefaultResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserMyPageDatasource @Inject constructor(
    private val loginService: CoHousService
) {
    suspend fun logout(): DefaultResponse {
        var response = DefaultResponse(200, "로그아웃 성공", 13)
        withContext(Dispatchers.IO) {
            runCatching {
                loginService.logout()
            }.onSuccess {
                response = it
                ChallengeApplication.getInstance().tokenManager.deleteToken()
            }.onFailure {
                Log.e(ContentValues.TAG, "Logout Failure")
            }
        }
        return response
    }
}