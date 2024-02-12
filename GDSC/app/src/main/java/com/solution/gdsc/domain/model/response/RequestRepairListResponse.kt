package com.solution.gdsc.domain.model.response

data class RequestRepairListResponse(
    val status: Int,
    val message: String,
    val data: RequestRepairListDto
)
