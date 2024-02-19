package com.solution.gdsc.domain.model.response

data class UpdateUserInfoResponse(
    val status: Int = 0,
    val message: String = "",
    val data: UpdateUserInfoDto?
)
