package com.solution.gdsc.domain.model.response

data class UpdateUserInfoResponse(
    val status: Int,
    val message: String,
    val data: UpdateUserInfoDto
)
