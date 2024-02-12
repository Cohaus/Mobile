package com.solution.gdsc.domain.model.response

data class RequestRepairDto(
    val content: List<RequestRepairItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
