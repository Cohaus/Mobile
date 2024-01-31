package com.solution.gdsc.data.source

import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.repository.UserMyPageRepository
import javax.inject.Inject

class UserMyPageRepositoryImpl @Inject constructor(
    private val dataSource: UserMyPageDatasource
) : UserMyPageRepository {
    override suspend fun logout(): DefaultResponse = dataSource.logout()
    override suspend fun getUserInfo(): UserInfoResponse = dataSource.getUserInfo()
}
