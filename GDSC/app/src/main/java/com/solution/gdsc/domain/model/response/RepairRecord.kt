package com.solution.gdsc.domain.model.response

data class RepairRecord(
    val content: List<RecordItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
