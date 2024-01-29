package com.solution.gdsc.data.source

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.LoginService
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.LoginDto
import com.solution.gdsc.domain.model.response.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun signUp(signUpRequest: SignUpRequest): DefaultResponse {
        var response = DefaultResponse(status = 200, message = "로그인 성공", data = 1)
        withContext(Dispatchers.IO) {
            runCatching {
                loginService.signUp(signUpRequest)
            }.onSuccess {
                response = it
            }.onFailure {
                Log.e(TAG, "SignUp Failure")
            }
        }
        return response
    }

    suspend fun login(loginReq: LoginReq): LoginResponse {
        var response = LoginResponse(status = 200, message = "요청에 성공",
            LoginDto(1, "sdaf", "asdf"))
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