package com.solution.gdsc.domain.model.response

data class SaveRecord(
    val content: List<RecordItem>,
    val hasNext: Boolean,
    val first: Boolean,
    val last: Boolean
)
