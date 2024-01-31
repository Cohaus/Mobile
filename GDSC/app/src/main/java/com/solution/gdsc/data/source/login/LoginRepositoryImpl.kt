package com.solution.gdsc.data.source.login

import com.solution.gdsc.data.source.login.LoginDataSource
import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) : LoginRepository {
    override suspend fun signUp(signUp: SignUpRequest): DefaultResponse = dataSource.signUp(signUp)

    override suspend fun login(loginReq: LoginReq): LoginResponse = dataSource.login(loginReq)

}