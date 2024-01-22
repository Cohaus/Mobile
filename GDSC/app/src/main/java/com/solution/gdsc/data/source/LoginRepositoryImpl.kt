package com.solution.gdsc.data.source

import com.solution.gdsc.domain.model.req.LoginReq
import com.solution.gdsc.domain.model.response.LoginResponse
import com.solution.gdsc.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val dataSource: LoginDataSource
) : LoginRepository {

    override suspend fun login(loginReq: LoginReq): LoginResponse = dataSource.login(loginReq)
}