package com.solution.gdsc.domain.model.response

data class CompleteRepair(
    val content: List<RepairItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
