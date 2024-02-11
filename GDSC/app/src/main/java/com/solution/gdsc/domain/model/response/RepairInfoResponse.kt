package com.solution.gdsc.domain.model.response

data class RepairInfoResponse(
    val status: Int,
    val message: String,
    val data: RepairInfoDto?
)
