package com.solution.gdsc.data.source

import com.solution.gdsc.domain.model.request.LoginReq
import com.solution.gdsc.domain.model.request.SignUpRequest
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.model.response.SignUpResponse
import com.solution.gdsc.domain.repository.LoginRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) : LoginRepository {
    override suspend fun signUp(signUp: SignUpRequest): SignUpResponse = dataSource.signUp(signUp)

    override suspend fun login(loginReq: LoginReq): Flow<LoginResponse> = dataSource.login(loginReq)

}