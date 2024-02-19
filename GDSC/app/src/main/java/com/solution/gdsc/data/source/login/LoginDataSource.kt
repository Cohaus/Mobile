package com.solution.gdsc.data.source.login

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.LoginService
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginDto
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun signUp(signUpRequest: SignUpRequest): Flow<SignUpResponse> = flow {
        val response = loginService.signUp(signUpRequest)
        emit(response)
    }.catch {
        Log.e(TAG, "SignUp Failure ${it.message.toString()}")
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