package com.solution.gdsc.data.source.login

import android.content.ContentValues.TAG
import android.util.Log
import com.solution.gdsc.data.remote.LoginService
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
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

    suspend fun login(loginReq: LoginReq): Flow<LoginResponse> = flow {
        val response = loginService.login(loginReq)
        emit(response)
    }.catch {
        Log.e(TAG, "Login Failure ${it.message.toString()}")
    }
}