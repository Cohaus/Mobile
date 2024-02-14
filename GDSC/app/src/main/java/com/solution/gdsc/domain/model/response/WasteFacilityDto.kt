package com.solution.gdsc.domain.model.response

data class WasteFacilityDto(
    val content: List<WasteFacilityItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
