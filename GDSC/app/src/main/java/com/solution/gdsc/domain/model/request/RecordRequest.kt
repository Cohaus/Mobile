package com.solution.gdsc.domain.model.request

data class RecordRequest(
    val title: String,
    val detail: String,
    val grade: String,
    val category: String,
    val image: String
)
