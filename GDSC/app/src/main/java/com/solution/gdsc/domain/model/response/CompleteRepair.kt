package com.solution.gdsc.domain.model.response

data class CompleteRepair(
    val content: RepairItem,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
