package com.solution.gdsc.domain.model.response

data class VolunteerRepairListResponse(
    val status: Int = 0,
    val message: String = "",
    val data: VolunteerRepairListDto?
)
