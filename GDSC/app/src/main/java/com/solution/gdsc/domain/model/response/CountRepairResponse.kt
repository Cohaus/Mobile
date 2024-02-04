package com.solution.gdsc.domain.model.response

data class CountRepairResponse(
    val status: String,
    val message: String,
    val data: List<CountRepairDto>
)
