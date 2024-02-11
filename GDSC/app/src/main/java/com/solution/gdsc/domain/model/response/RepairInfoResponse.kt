package com.solution.gdsc.domain.model.response

data class RepairInfoResponse(
    val status: Int = 0,
    val message: String = "",
    val data: RepairInfoDto?
)
