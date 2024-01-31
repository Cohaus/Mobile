package com.solution.gdsc.domain.model.response

data class UserInfoResponse(
    val status: Int,
    val message: String,
    val data: UserInfoDto
)