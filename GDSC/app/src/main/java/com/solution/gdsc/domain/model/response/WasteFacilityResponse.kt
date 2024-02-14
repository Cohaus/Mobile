package com.solution.gdsc.domain.model.response

data class WasteFacilityResponse(
    val status: Int = 0,
    val message: String = "",
    val data: WasteFacilityDto?
)
