package com.solution.gdsc.data.source

import com.solution.gdsc.domain.model.request.UpdateUserInfoRequest
import com.solution.gdsc.domain.model.response.DefaultResponse
import com.solution.gdsc.domain.model.response.UpdateUserInfoResponse
import com.solution.gdsc.domain.model.response.UserInfoResponse
import com.solution.gdsc.domain.model.response.UserRecordResponse
import com.solution.gdsc.domain.repository.UserMyPageRepository
import javax.inject.Inject

class UserMyPageRepositoryImpl @Inject constructor(
    private val dataSource: UserMyPageDatasource
) : UserMyPageRepository {
    override suspend fun logout(): DefaultResponse = dataSource.logout()
    override suspend fun getUserInfo(): UserInfoResponse = dataSource.getUserInfo()
    override suspend fun updateUserInfo(updateUserInfoRequest: UpdateUserInfoRequest): UpdateUserInfoResponse = dataSource.updateUserInfo(updateUserInfoRequest)
    override suspend fun withdraw(): DefaultResponse = dataSource.withdraw()
    override suspend fun getUserRecord(): UserRecordResponse = dataSource.getUserRecord()
}
