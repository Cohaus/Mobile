package com.solution.gdsc.domain.model.response

data class WasteFacilityResponse(
    val status: Int,
    val message: String,
    val data: WasteFacilityDto
)
