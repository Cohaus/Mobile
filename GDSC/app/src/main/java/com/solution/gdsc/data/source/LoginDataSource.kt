package com.solution.gdsc.data.source

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.LoginService
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class LoginDataSource @Inject constructor(
    private val loginService: LoginService
) {

    suspend fun signUp(signUpRequest: SignUpRequest): SignUpResponse {
        var response = SignUpResponse("123","213 ")
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

    suspend fun login(loginReq: LoginReq): Flow<LoginResponse> = flow {
        try {
            val response = withContext(Dispatchers.IO) {
                loginService.login(loginReq)
            }
            emit(response)
        } catch (e: Exception) {
            Log.e(TAG, "Login Failure", e)
        }
    }
}