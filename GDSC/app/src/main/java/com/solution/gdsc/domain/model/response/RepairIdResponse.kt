package com.solution.gdsc.domain.model.response

data class RepairIdResponse(
    val status: Int = 0,
    val message: String = "",
    val data: RepairId?
)
