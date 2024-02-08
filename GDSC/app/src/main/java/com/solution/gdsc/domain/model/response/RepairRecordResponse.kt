package com.solution.gdsc.domain.model.response

data class RepairRecordResponse(
    val status: Int = 0,
    val message: String = "",
    val data: RepairRecordDto?
)
