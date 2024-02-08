package com.solution.gdsc.domain.model.response

data class RepairRecordResponse(
    val status: Int,
    val message: String,
    val data: RepairRecordDto
)
