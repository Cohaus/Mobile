package com.solution.gdsc.domain.model.response

data class UserRecordResponse(
    val status: Int,
    val message: String,
    val data: UserRecordListResponse
)
