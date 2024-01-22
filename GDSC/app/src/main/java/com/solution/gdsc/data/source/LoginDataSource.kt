package com.solution.gdsc.data.source

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.LoginService
import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun login(loginReq: LoginReq): LoginResponse {
        var response = LoginResponse(userId = 1L, accessToken = "aa", "bb")
        withContext(Dispatchers.IO) {
            runCatching {
                loginService.login(loginReq)
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "Login Failure")
            }
        }
        return response
    }
}