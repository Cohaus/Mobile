package com.solution.gdsc.domain.model.response

data class ProceedingRepair(
    val content: List<RepairItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
