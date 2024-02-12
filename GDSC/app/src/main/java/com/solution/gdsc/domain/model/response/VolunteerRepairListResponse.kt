package com.solution.gdsc.domain.model.response

data class VolunteerRepairListResponse(
    val status: Int,
    val message: String,
    val data: VolunteerRepairListDto
)
