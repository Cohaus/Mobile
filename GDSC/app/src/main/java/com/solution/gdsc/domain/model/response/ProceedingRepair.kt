package com.solution.gdsc.domain.model.response

data class ProceedingRepair(
    val content: RepairItem,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
